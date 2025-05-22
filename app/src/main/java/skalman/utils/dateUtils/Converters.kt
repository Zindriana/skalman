package skalman.utils.dateUtils

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import skalman.data.models.IgnoreRule
import skalman.data.models.RecurrenceRule
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Converters {

    private val json = Json {
        ignoreUnknownKeys = true
        classDiscriminator = "type"
    }

    private val dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
    private val dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE

    // --- LocalDateTime ---
    @TypeConverter
    fun fromLocalDateTime(value: LocalDateTime?): String? =
        value?.format(dateTimeFormatter)

    @TypeConverter
    fun toLocalDateTime(value: String?): LocalDateTime? =
        value?.let { LocalDateTime.parse(it, dateTimeFormatter) }

    // --- LocalDate ---
    @TypeConverter
    fun fromLocalDate(value: LocalDate?): String? =
        value?.format(dateFormatter)

    @TypeConverter
    fun toLocalDate(value: String?): LocalDate? =
        value?.let { LocalDate.parse(it, dateFormatter) }

    // --- RecurrenceRule ---
    @TypeConverter
    fun recurrenceRuleToJson(rule: RecurrenceRule?): String? =
        rule?.let { json.encodeToString(it) }

    @TypeConverter
    fun recurrenceRuleFromJson(jsonString: String?): RecurrenceRule? =
        jsonString?.let { json.decodeFromString(it) }

    // --- IgnoreRule list ---
    @TypeConverter
    fun ignoreRulesToJson(rules: List<IgnoreRule>?): String? =
        rules?.let { json.encodeToString(it) }

    @TypeConverter
    fun ignoreRulesFromJson(jsonString: String?): List<IgnoreRule>? =
        jsonString?.let { json.decodeFromString(it) }
}
