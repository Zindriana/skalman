package skalman.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import skalman.data.models.CalendarAlarm

@Dao
interface CalendarAlarmDao {

    @Query("SELECT * FROM calendar_alarms ORDER BY startTime ASC")
    fun getAllAlarms(): Flow<List<CalendarAlarm>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlarmReturningId(alarm: CalendarAlarm): Long

    @Delete
    suspend fun deleteAlarm(alarm: CalendarAlarm)

    @Update
    suspend fun updateAlarm(alarm: CalendarAlarm)
}
