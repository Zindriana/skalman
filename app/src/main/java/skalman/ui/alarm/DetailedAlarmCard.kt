package skalman.ui.alarm

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import skalman.data.models.CalendarAlarm

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailedAlarmCard(alarm: CalendarAlarm) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(alarm.title) }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {

            }
        }
    }
}