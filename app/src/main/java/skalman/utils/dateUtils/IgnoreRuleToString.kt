package skalman.utils.dateUtils


import skalman.data.models.IgnoreRule
import java.time.format.DateTimeFormatter
import kotlin.collections.sorted

fun IgnoreRule.toReadableString(): String = when (this) {
    is IgnoreRule.IgnoreDates -> {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val list = dates.sorted().joinToString(", ") { it.format(formatter) }
        "Hoppa över datum: $list"
    }

    is IgnoreRule.IgnoreWeekdays -> {
        if (days.isEmpty()) {
            "Hoppa över veckodagar: inga valda"
        } else {
            "Hoppa över veckodagar: ${days.toWeekdayString()}"
        }
    }
}
