package skalman.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import skalman.data.repo.AlarmRepository
import skalman.ui.calendar.CalendarScreen
import skalman.ui.main.components.DateTimeDisplay
import skalman.ui.alarm.screens.AddAlarmScreen
import skalman.viewmodel.CalendarViewModel
import skalman.utils.alarmUtils.AlarmScheduler
import skalman.data.models.CalendarAlarm
import skalman.ui.alarm.screens.DetailedAlarmScreen
import skalman.ui.alarm.screens.EditAlarmScreen

enum class MainScreenDestination {
    Calendar, AddAlarm, Focus, AlarmDetail, EditAlarm, Todo
} //Focus och To-do är inlagda här sålänge i väntan på att FocusScreen och TodoScreen skapas i en framtida version

@Composable
fun MainScreen(repository: AlarmRepository) {
    val context = LocalContext.current
    val viewModel = remember {
        CalendarViewModel(repository, AlarmScheduler(context))
    }

    var currentScreen by remember { mutableStateOf(MainScreenDestination.Calendar) }
    var selectedAlarm by remember { mutableStateOf<CalendarAlarm?>(null) }
    var alarmToEdit by remember { mutableStateOf<CalendarAlarm?>(null) }

    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            DateTimeDisplay(modifier = Modifier.align(Alignment.TopEnd))
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(2.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) { //texterna skulle kunna bytas ut mot symboler/ikoner
            Button(onClick = { currentScreen = MainScreenDestination.Calendar }) {
                Text("Kalender")
            }
            Button(onClick = { currentScreen = MainScreenDestination.AddAlarm }) {
                Text("Nytt Alarm")
            }
            Button(onClick = { currentScreen = MainScreenDestination.Focus }) {
                Text("Fokus")
            }
            Button(onClick = { currentScreen = MainScreenDestination.Todo }) {
                Text("Todo")
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            when (currentScreen) {
                MainScreenDestination.Calendar -> CalendarScreen(
                    viewModel = viewModel,
                    onAlarmClick = { alarm ->
                        selectedAlarm = alarm
                        currentScreen = MainScreenDestination.AlarmDetail
                    }
                )

                MainScreenDestination.AddAlarm -> AddAlarmScreen(viewModel, onBack = { currentScreen = MainScreenDestination.Calendar })

                MainScreenDestination.Focus -> AddAlarmScreen(viewModel, onBack = { currentScreen = MainScreenDestination.Calendar }) // placeholder
                MainScreenDestination.Todo -> AddAlarmScreen(viewModel, onBack = { currentScreen = MainScreenDestination.Calendar }) // placeholder

                MainScreenDestination.AlarmDetail -> selectedAlarm?.let {
                    DetailedAlarmScreen(
                        alarm = it,
                        onDelete = { alarm ->
                            viewModel.deleteAlarm(alarm)
                            currentScreen = MainScreenDestination.Calendar
                        },
                        onUpdate = { alarm ->
                            alarmToEdit = alarm
                            currentScreen = MainScreenDestination.EditAlarm
                        }
                    )
                }

                MainScreenDestination.EditAlarm -> alarmToEdit?.let {
                    EditAlarmScreen(
                        viewModel = viewModel,
                        alarmToEdit = it,
                        onBack = { currentScreen = MainScreenDestination.Calendar }
                    )
                }
            }
        }
    }
}
