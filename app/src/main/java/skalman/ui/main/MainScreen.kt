package skalman.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import skalman.ui.calendar.CalendarScreen
import skalman.ui.main.components.DateTimeDisplay

@Composable
fun MainScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        CalendarScreen()
        DateTimeDisplay(modifier = Modifier.align(Alignment.TopEnd))
    }
}