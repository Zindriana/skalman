package skalman.data.models


import kotlinx.serialization.Serializable
import java.time.DayOfWeek

@Serializable
sealed class RecurrenceRule {
    @Serializable
    data class IntervalDays(val everyXDays: Int) : RecurrenceRule()

    @Serializable
    data class Weekly(val daysOfWeek: Set<DayOfWeek>) : RecurrenceRule()

    @Serializable
    data class Monthly(val daysOfMonth: Set<Int>) : RecurrenceRule()
}
