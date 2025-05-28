package skalman.utils.dateUtils

import androidx.room.TypeConverter
import kotlinx.serialization.Contextual
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import skalman.data.models.IgnoreRule
import skalman.data.models.RecurrenceRule
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

// ✅ Registrera LocalDateSerializer i SerializersModule
private val serializersModule = SerializersModule {
    contextual(LocalDate::class, LocalDateSerializer)
}

// ✅ Använd rätt Json-instans med module
private val json = Json {
    ignoreUnknownKeys = true
    classDiscriminator = "type"
    serializersModule = serializersModule
}

private val dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
private val dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE

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
