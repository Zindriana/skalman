package skalman.ui.calendar

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import skalman.data.models.CalendarAlarm

// Dummydata – tom lista tills ViewModel är inkopplad
private val dummyAlarms = emptyList<CalendarAlarm>()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Din kalender") }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
        ) {
            if (dummyAlarms.isEmpty()) {
                Text(
                    text = "Inga aktiviteter än",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                LazyColumn {
                    items(dummyAlarms) { alarm ->
                        AlarmCard(alarm)
                    }
                }
            }
        }
    }
}