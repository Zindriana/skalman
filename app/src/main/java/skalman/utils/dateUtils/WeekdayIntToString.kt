package skalman.utils.dateUtils

import java.time.DayOfWeek
import java.time.format.TextStyle
import java.util.Locale

fun Set<Int>.toWeekdayString(locale: Locale = Locale("sv")): String {
    return this.sorted()
        .mapNotNull { runCatching { DayOfWeek.of(it) }.getOrNull() }
        .joinToString(", ") { it.getDisplayName(TextStyle.FULL, locale) }
}
