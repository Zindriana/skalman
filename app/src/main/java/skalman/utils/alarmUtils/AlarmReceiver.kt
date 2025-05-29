package skalman.utils.alarmUtils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import skalman.R

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // Hämta information från intent
        val title = intent.getStringExtra("title") ?: "Alarm"
        val alarmId = intent.getIntExtra("alarm_id", -999)

        // 🔍 Logga mer detaljerat
        Log.d("AlarmReceiver", ">>> Alarm RECEIVED! ID: $alarmId, Title: $title")

        // 🛎️ Visa Toast (för enkel visuell feedback)
        Toast.makeText(context, "Alarm: $title", Toast.LENGTH_LONG).show()

        // 🔔 Skapa NotificationChannel om API 26+
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(
            "alarm_channel",
            "Alarm Notifications",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Används för att visa alarmnotifikationer"
        }
        notificationManager.createNotificationChannel(channel)

        // 📨 Skapa och visa notifikation
        val notification = NotificationCompat.Builder(context, "alarm_channel")
            .setSmallIcon(R.drawable.ic_launcher_foreground) // byt gärna till en egen ikon
            .setContentTitle("Alarm")
            .setContentText("Alarm: $title")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        // Använd alarmId som notificationId för att undvika krockar
        notificationManager.notify(alarmId, notification)
    }
}
