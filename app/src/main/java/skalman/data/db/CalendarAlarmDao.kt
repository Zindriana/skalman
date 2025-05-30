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
    //lade in så att Id returneras för att kunna kontrollera och undvika att id = 0
    // detta för att om användaren lägger in ett pre-alarm så skapas det kodmässigt två id,
    // där pre-alarm får den negativa motsvarigheten av huvudalarmets id.
    // Ex, huvudalarmet får id 2, då får föralarmet id -2.
    // Vilket blir problematiskt vid fallet 0

    @Delete
    suspend fun deleteAlarm(alarm: CalendarAlarm)

    @Update
    suspend fun updateAlarm(alarm: CalendarAlarm)
}
