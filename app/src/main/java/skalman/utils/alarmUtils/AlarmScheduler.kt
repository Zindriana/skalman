package skalman.utils.alarmUtils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import skalman.data.models.CalendarAlarm
import java.time.ZoneId

//Alla tre alarmUtils är skapade med stor hjälp av AI

class AlarmScheduler(private val context: Context) {

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun scheduleAlarm(alarm: CalendarAlarm) {
        // 🟢 Schedule main alarm
        scheduleExactAlarm(
            requestCode = alarm.id,
            triggerAtMillis = alarm.startTime.toEpochMillis(),
            message = alarm.title
        )

        // 🟡 Schedule pre-alarm if needed
        if (alarm.preAlarmMinutes > 0) {
            val preAlarmTime = alarm.startTime.minusMinutes(alarm.preAlarmMinutes.toLong())
            scheduleExactAlarm(
                requestCode = -alarm.id,
                triggerAtMillis = preAlarmTime.toEpochMillis(),
                message = "Förbered avslut"
            )
        }
    }

    fun cancelAlarm(alarm: CalendarAlarm) {
        // ❌ Cancel both alarm and pre-alarm if applicable
        val idsToCancel = listOf(alarm.id, -alarm.id)

        for (requestCode in idsToCancel) {
            val intent = Intent(context, AlarmReceiver::class.java).apply {
                action = "skalman.alarm.ACTION_$requestCode"
            }

            val pendingIntent = PendingIntent.getBroadcast(
                context,
                requestCode,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            alarmManager.cancel(pendingIntent)
        }
    }

    private fun scheduleExactAlarm(
        requestCode: Int,
        triggerAtMillis: Long,
        message: String
    ) {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            action = "skalman.alarm.ACTION_$requestCode" // 🔑 Gör varje PendingIntent unik
            putExtra("title", message)
            putExtra("alarm_id", requestCode)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        try {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                triggerAtMillis,
                pendingIntent
            )
        } catch (e: SecurityException) {
            Log.e("AlarmScheduler", "Permission denied to schedule exact alarm", e)
        }
    }

    private fun java.time.LocalDateTime.toEpochMillis(): Long {
        return atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
    }
}
