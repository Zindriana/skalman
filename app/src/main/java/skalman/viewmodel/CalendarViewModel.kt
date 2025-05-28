package skalman.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import skalman.data.models.CalendarAlarm
import skalman.data.models.DayWithAlarms
import skalman.data.repo.AlarmRepository
import skalman.utils.alarmUtils.AlarmScheduler

class CalendarViewModel(
    private val repository: AlarmRepository,
    private val scheduler: AlarmScheduler
) : ViewModel() {

    private val _selectedPeriod = MutableStateFlow(7)
    val selectedPeriod: StateFlow<Int> = _selectedPeriod

    private val _groupedAlarms = MutableStateFlow<List<DayWithAlarms>>(emptyList())
    val groupedAlarms: StateFlow<List<DayWithAlarms>> = _groupedAlarms

    fun onPeriodSelected(days: Int) {
        _selectedPeriod.value = days
        loadGroupedAlarms()
    }

    fun loadGroupedAlarms() {
        viewModelScope.launch {
            repository.alarms.collect { list ->
                val grouped = list
                    .groupBy { it.startTime.toLocalDate() }
                    .map { (date, alarms) -> DayWithAlarms(date, alarms) }
                    .sortedBy { it.date }

                _groupedAlarms.value = grouped
            }
        }
    }

    fun addAlarm(alarm: CalendarAlarm) {
        viewModelScope.launch {
            repository.addAlarm(alarm)
            scheduler.scheduleAlarm(alarm)
            loadGroupedAlarms()
        }
    }

    fun deleteAlarm(alarm: CalendarAlarm) {
        viewModelScope.launch {
            repository.deleteAlarm(alarm)
            scheduler.cancelAlarm(alarm)
            loadGroupedAlarms()
        }
    }
}
