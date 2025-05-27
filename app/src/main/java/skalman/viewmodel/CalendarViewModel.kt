package skalman.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import skalman.data.models.CalendarAlarm
import skalman.data.models.DayWithAlarms
import skalman.data.repo.AlarmRepository
import skalman.utils.alarmUtils.AlarmScheduler
import java.time.LocalDate

class CalendarViewModel(
    private val repository: AlarmRepository
) : ViewModel() {

    private val _selectedPeriod = MutableStateFlow(7)
    val selectedPeriod: StateFlow<Int> = _selectedPeriod.asStateFlow()

    val alarms: StateFlow<List<CalendarAlarm>> = repository.alarms
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val groupedAlarms: StateFlow<List<DayWithAlarms>> = combine(
        alarms,
        selectedPeriod
    ) { alarmList, days ->
        val today = LocalDate.now()
        val end = today.plusDays(days.toLong())

        alarmList
            .filter { it.startTime.toLocalDate() in today..end }
            .groupBy { it.startTime.toLocalDate() }
            .map { (date, list) -> DayWithAlarms(date, list) }
            .sortedBy { it.date }

    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun onPeriodSelected(period: Int) {
        _selectedPeriod.value = period
    }

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
