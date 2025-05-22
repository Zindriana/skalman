package skalman.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import skalman.data.models.CalendarAlarm
import skalman.data.repo.AlarmRepository
import skalman.utils.alarmUtils.AlarmScheduler

class CalendarViewModel(private val repository: AlarmRepository) : ViewModel() {

    val alarms: StateFlow<List<CalendarAlarm>> = repository.alarms
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addAlarm(alarm: CalendarAlarm, scheduler: AlarmScheduler) {
        viewModelScope.launch {
            repository.addAlarm(alarm)
            scheduler.scheduleAlarm(alarm)
        }
    }

    fun deleteAlarm(alarm: CalendarAlarm, scheduler: AlarmScheduler) {
        viewModelScope.launch {
            repository.deleteAlarm(alarm)
            scheduler.cancelAlarm(alarm)
        }
    }
}
