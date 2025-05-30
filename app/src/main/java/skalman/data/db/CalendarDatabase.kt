package skalman.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import skalman.data.models.CalendarAlarm
import skalman.utils.dateUtils.Converters

@Database(
    entities = [CalendarAlarm::class],
    version = 2,
    exportSchema = false
    // Testade i början av projektet att ha true här för att kunna skapa schemas,
    // så att användare skulle kunna uppdatera appen till nyare versioner.
    // Valde att skippa det nu pga tidshorizonten på projektet,
    // men kommer att lägga in det i framtiden igen vid fortsatt utveckling av den här appen
)
@TypeConverters(Converters::class)
abstract class CalendarDatabase : RoomDatabase() {
    abstract fun alarmDao(): CalendarAlarmDao
}
