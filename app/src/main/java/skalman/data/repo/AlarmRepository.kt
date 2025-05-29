package skalman.data.repo

import kotlinx.coroutines.flow.Flow
import skalman.data.models.CalendarAlarm
import skalman.data.db.CalendarAlarmDao

class AlarmRepository(private val alarmDao: CalendarAlarmDao) {

    val alarms: Flow<List<CalendarAlarm>> = alarmDao.getAllAlarms()

    // Returnerar det nya ID:t från Room
    suspend fun addAlarm(alarm: CalendarAlarm): Long {
        return alarmDao.insertAlarmReturningId(alarm)
    }

    suspend fun deleteAlarm(alarm: CalendarAlarm) {
        alarmDao.deleteAlarm(alarm)
    }

    suspend fun updateAlarm(alarm: CalendarAlarm) {
        alarmDao.updateAlarm(alarm)
    }
}
