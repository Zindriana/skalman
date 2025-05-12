package skalman.data.models

import java.time.LocalDate

data class DayWithAlarms(
        val date: LocalDate,
        val alarms: List<CalendarAlarm>
)


