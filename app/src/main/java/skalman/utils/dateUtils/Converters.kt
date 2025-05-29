package skalman.utils.dateUtils

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import skalman.data.models.IgnoreRule
import skalman.data.models.RecurrenceRule
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private val json = Json {
    ignoreUnknownKeys = true
    classDiscriminator = "type"
}

private val dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

object Converters {

    @TypeConverter
    fun fromLocalDateTime(value: LocalDateTime?): String? =
        value?.format(dateTimeFormatter)

    @TypeConverter
    fun toLocalDateTime(value: String?): LocalDateTime? =
        value?.let { LocalDateTime.parse(it, dateTimeFormatter) }

    @TypeConverter
    fun recurrenceRuleToJson(rule: RecurrenceRule?): String? =
        rule?.let { json.encodeToString(it) }

    @TypeConverter
    fun recurrenceRuleFromJson(jsonString: String?): RecurrenceRule? =
        jsonString?.let { json.decodeFromString(it) }

    @TypeConverter
    fun ignoreRulesToJson(rules: List<IgnoreRule>?): String? =
        rules?.let { json.encodeToString(it) }

    @TypeConverter
    fun ignoreRulesFromJson(jsonString: String?): List<IgnoreRule>? =
        jsonString?.let { json.decodeFromString(it) }
}
