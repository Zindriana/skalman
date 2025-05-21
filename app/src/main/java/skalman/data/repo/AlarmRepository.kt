package skalman.data.repo

import kotlinx.coroutines.flow.Flow
import skalman.data.db.CalendarAlarmDao
import skalman.data.models.CalendarAlarm

class AlarmRepository(private val dao: CalendarAlarmDao) {

    val alarms: Flow<List<CalendarAlarm>> = dao.getAllAlarms()

    suspend fun addAlarm(alarm: CalendarAlarm) {
        dao.insertAlarm(alarm)
    }

    suspend fun deleteAlarm(alarm: CalendarAlarm) {
        dao.deleteAlarm(alarm)
    }
}
