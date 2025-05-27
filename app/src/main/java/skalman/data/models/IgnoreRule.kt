package skalman.data.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.Contextual
import java.time.LocalDate

@Serializable
sealed class IgnoreRule {

    @Serializable
    data class IgnoreWeekdays(val days: Set<Int>) : IgnoreRule()

    @Serializable
    data class IgnoreDates(
        @Contextual val dates: Set<LocalDate>
    ) : IgnoreRule()
}
