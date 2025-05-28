package skalman.ui.main

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
import skalman.ui.alarm.AddAlarmScreen
import skalman.ui.alarm.DetailedAlarmCard
// import skalman.ui.focus.FocusScreen
import skalman.viewmodel.CalendarViewModel
import skalman.utils.alarmUtils.AlarmScheduler
import skalman.data.models.CalendarAlarm

enum class MainScreenDestination {
    Calendar, AddAlarm, Focus, AlarmDetail
}

@Composable
fun MainScreen(repository: AlarmRepository) {
    val context = LocalContext.current
    val viewModel = remember {
        CalendarViewModel(repository, AlarmScheduler(context))
    }

    var currentScreen by remember { mutableStateOf(MainScreenDestination.Calendar) }
    var selectedAlarm by remember { mutableStateOf<CalendarAlarm?>(null) }


    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { currentScreen = MainScreenDestination.Calendar }) {
                Text("Kalender")
            }
            Button(onClick = { currentScreen = MainScreenDestination.AddAlarm }) {
                Text("Lägg till Alarm")
            }
            Button(onClick = { currentScreen = MainScreenDestination.Focus }) {
                Text("Fokus")
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
                MainScreenDestination.AddAlarm -> AddAlarmScreen(viewModel)
                MainScreenDestination.Focus -> AddAlarmScreen(viewModel) // placeholder
                MainScreenDestination.AlarmDetail -> selectedAlarm?.let { DetailedAlarmCard(it) }
            }

            DateTimeDisplay(modifier = Modifier.align(Alignment.TopEnd))
        }
    }
}
