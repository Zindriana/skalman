package skalman.utils.dateUtils

import skalman.data.models.RecurrenceRule
import java.time.format.TextStyle
import java.util.Locale

fun RecurrenceRule.toReadableString(): String {
    return when (this) {
        is RecurrenceRule.IntervalDays -> {
            if (everyXDays == 1) "Varje dag" else "Var $everyXDays:e dag"
        }
        is RecurrenceRule.Weekly -> {
            if (daysOfWeek.isEmpty()) {
                "Varje vecka (inga dagar valda)"
            } else {
                val days = daysOfWeek
                    .sorted()
                    .joinToString(", ") { it.getDisplayName(TextStyle.FULL, Locale("sv")) }
                "Varje vecka på: $days"
            }
        }
        is RecurrenceRule.Monthly -> {
            if (daysOfMonth.isEmpty()) {
                "Varje månad (inga datum valda)"
            } else {
                val days = daysOfMonth.sorted().joinToString(", ")
                "Varje månad den: $days"
            }
        }
    }
}