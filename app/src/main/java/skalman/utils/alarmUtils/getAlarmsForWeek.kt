//this isn´t used and can probably be deleted. The functionality is handled by others now

import skalman.data.models.CalendarAlarm

import skalman.data.models.DayWithAlarms
import skalman.utils.dateUtils.getStartOfWeek
import java.time.LocalDate

fun getAlarmsForWeek(referenceDate: LocalDate, allAlarms: List<CalendarAlarm>): List<DayWithAlarms> {
    val startOfWeek = getStartOfWeek(referenceDate)

    return (0..6).map { offset ->
        val currentDate = startOfWeek.plusDays(offset.toLong())
        val alarmsForDay = allAlarms.filter { it.startTime.toLocalDate() == currentDate }

        DayWithAlarms(date = currentDate, alarms = alarmsForDay)
    }
}