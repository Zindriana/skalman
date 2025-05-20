package skalman.utils.dateUtils

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Converters {
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    @TypeConverter
    fun fromString(value: String?): LocalDateTime? =
        value?.let { LocalDateTime.parse(it, formatter) }

    @TypeConverter
    fun toString(dateTime: LocalDateTime?): String? =
        dateTime?.format(formatter)
}
