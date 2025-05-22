package skalman.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import skalman.data.models.CalendarAlarm
import skalman.utils.dateUtils.Converters

@Database(
    entities = [CalendarAlarm::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class CalendarDatabase : RoomDatabase() {
    abstract fun alarmDao(): CalendarAlarmDao
}
