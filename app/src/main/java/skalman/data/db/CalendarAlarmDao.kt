package skalman.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import skalman.data.models.CalendarAlarm

@Dao
interface CalendarAlarmDao {

    @Query("SELECT * FROM calendar_alarms ORDER BY startTime ASC")
    fun getAllAlarms(): Flow<List<CalendarAlarm>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlarm(alarm: CalendarAlarm)

    @Delete
    suspend fun deleteAlarm(alarm: CalendarAlarm)
}
