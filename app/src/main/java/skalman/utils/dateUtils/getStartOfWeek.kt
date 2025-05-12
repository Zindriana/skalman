package skalman.utils.dateUtils

import java.time.DayOfWeek
import java.time.LocalDate

fun getStartOfWeek(date: LocalDate): LocalDate {
    return date.with(DayOfWeek.MONDAY)
}