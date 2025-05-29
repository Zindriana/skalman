package skalman.data.models

import kotlinx.serialization.Serializable

@Serializable
sealed class RecurrenceRule {

    @Serializable
    data class IntervalDays(val everyXDays: Int) : RecurrenceRule()

    @Serializable
    data class Weekly(
        val daysOfWeek: Set<Int>,
        val moduloWeek: Int? = null
    ) : RecurrenceRule()

    @Serializable
    data class Monthly(val daysOfMonth: Set<Int>) : RecurrenceRule()
}

