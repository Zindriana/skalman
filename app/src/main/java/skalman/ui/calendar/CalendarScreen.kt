package skalman.ui.calendar

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import skalman.data.models.CalendarAlarm
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

// Dummydata – tom lista tills ViewModel är inkopplad
private val dummyAlarms = emptyList<CalendarAlarm>()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen() {
    var selectedView by remember { mutableStateOf("week") }

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
            DateViewButton(
                selectedView = selectedView,
                onViewModeChange = { selectedView = it }
            )
            when (selectedView) {
                "day" -> Text("Dagvy (kommer senare)", modifier = Modifier.padding(16.dp))
                "week" -> {
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
                "month" -> Text("Månadsvy (kommer senare)", modifier = Modifier.padding(16.dp))
            }
        }
    }
}