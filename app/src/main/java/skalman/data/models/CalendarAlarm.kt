package skalman.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "calendar_alarms")
data class CalendarAlarm(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String = "Tom titel",
    val description: String? = null,
    val startTime: LocalDateTime,
    val preAlarmMinutes: Int = 0,
    val recurrenceRules: RecurrenceRule? = null,
    val ignoreRules: List<IgnoreRule>? = null,
    val colorTag: String? = null,
    val alarmSound: String = "default",
    val notes: String? = null
)

