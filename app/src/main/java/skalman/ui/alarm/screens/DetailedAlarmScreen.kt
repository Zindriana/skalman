package skalman.ui.alarm.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import skalman.data.models.CalendarAlarm
import skalman.ui.alarm.DetailedAlarmCard

@Composable
fun DetailedAlarmScreen(
    alarm: CalendarAlarm,
    onDelete: (CalendarAlarm) -> Unit,
    onUpdate: (CalendarAlarm) -> Unit
) {
    Scaffold(
        topBar = {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                color = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)
                ) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Alarmdetaljer",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            color = MaterialTheme.colorScheme.background
        ) {
            DetailedAlarmCard(
                alarm = alarm,
                onDelete = onDelete,
                onUpdate = onUpdate
            )
        }
    }
}
