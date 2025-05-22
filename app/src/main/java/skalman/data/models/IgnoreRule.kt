package skalman.data.models

import kotlinx.serialization.Serializable
import java.time.DayOfWeek
import java.time.LocalDate

@Serializable
sealed class IgnoreRule {
    @Serializable
    data class IgnoreWeekdays(val days: Set<DayOfWeek>) : IgnoreRule()

    @Serializable
    data class IgnoreDates(val dates: Set<LocalDate>) : IgnoreRule()
}
