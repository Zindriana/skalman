package skalman.ui.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import skalman.data.models.CalendarAlarm
import skalman.data.repo.AlarmRepository

class CalendarViewModel(private val repository: AlarmRepository) : ViewModel() {

    val alarms: StateFlow<List<CalendarAlarm>> = repository.alarms
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addAlarm(alarm: CalendarAlarm) {
        viewModelScope.launch {
            repository.addAlarm(alarm)
        }
    }

    fun deleteAlarm(alarm: CalendarAlarm) {
        viewModelScope.launch {
            repository.deleteAlarm(alarm)
        }
    }
}
