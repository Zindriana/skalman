package skalman.utils.dateUtils

import skalman.data.models.RecurrenceRule

fun RecurrenceRule.toReadableString(): String {
    return when (this) {
        is RecurrenceRule.IntervalDays -> {
            if (everyXDays == 1) "Varje dag" else "Var $everyXDays:e dag"
        }

        is RecurrenceRule.Weekly -> {
            if (daysOfWeek.isEmpty()) {
                "Varje vecka (inga dagar valda)"
            } else {
                "Varje vecka på: ${daysOfWeek.toWeekdayString()}"
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
