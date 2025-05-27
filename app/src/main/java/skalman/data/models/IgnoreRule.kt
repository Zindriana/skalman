@file:UseSerializers(skalman.utils.dateUtils.LocalDateSerializer::class)

package skalman.data.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.LocalDate

@Serializable
sealed class IgnoreRule {

    @Serializable
    data class IgnoreWeekdays(
        val days: Set<Int>
    ) : IgnoreRule()

    @Serializable
    data class IgnoreDates(
        val dates: Set<LocalDate>
    ) : IgnoreRule()
}
