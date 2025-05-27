package skalman.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import skalman.data.repo.AlarmRepository
import skalman.ui.calendar.CalendarScreen
import skalman.ui.main.components.DateTimeDisplay
import skalman.viewmodel.CalendarViewModel

@Composable
fun MainScreen(repository: AlarmRepository) {
    val viewModel = remember {
        CalendarViewModel(repository)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        CalendarScreen(viewModel)
        DateTimeDisplay(modifier = Modifier.align(Alignment.TopEnd))
    }
}
