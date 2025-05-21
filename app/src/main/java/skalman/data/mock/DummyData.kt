package skalman.data.mock

import skalman.data.models.CalendarAlarm
import skalman.data.models.DayWithAlarms
import java.time.LocalDate
import java.time.LocalDateTime

fun getDummyDayWithAlarms(period: Int): List<DayWithAlarms> {
    val today = LocalDate.now()

    return List(period) { offset ->
        val date = today.plusDays(offset.toLong())

        val alarms = if (offset % 2 == 0) {
            listOf(
                CalendarAlarm(
                    title = "Aktivitet ${offset + 1}",
                    description = "Beskrivning för dag ${offset + 1}",
                    startTime = LocalDateTime.of(date.year, date.month, date.dayOfMonth, 9 + offset % 4, 0),
                    preAlarmMinutes = 10 + offset,
                    recurrenceRules = listOf("Varje vecka"),
                    notes = "Anteckning för aktivitet ${offset + 1}",
                    alarmSound = "default.mp3"
                )
            )
        } else emptyList()

        DayWithAlarms(date = date, alarms = alarms)
    }
}
