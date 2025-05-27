package skalman.ui.calendar

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import skalman.ui.calendar.components.CalendarPeriodView
import skalman.ui.calendar.components.DateViewButton
import skalman.viewmodel.CalendarViewModel
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(viewModel: CalendarViewModel = hiltViewModel()) {
    val selectedPeriod by viewModel.selectedPeriod.collectAsState()
    val groupedAlarms by viewModel.groupedAlarms.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Din kalender") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            DateViewButton(
                selectedView = selectedPeriod,
                onViewModeChange = { viewModel.onPeriodSelected(it) }
            )

            CalendarPeriodView(
                period = selectedPeriod,
                data = groupedAlarms
            )
        }
    }
}

