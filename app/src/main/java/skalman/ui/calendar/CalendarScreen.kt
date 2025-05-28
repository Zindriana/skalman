package skalman.ui.calendar

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import skalman.viewmodel.CalendarViewModel
import skalman.ui.calendar.components.CalendarPeriodView
import skalman.ui.calendar.components.DateViewButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(viewModel: CalendarViewModel) {
    val selectedPeriod by viewModel.selectedPeriod.collectAsState(initial = 7)
    val groupedAlarms by viewModel.groupedAlarms.collectAsState(initial = emptyList())

    LaunchedEffect(Unit) {
        viewModel.loadGroupedAlarms()
    }

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

