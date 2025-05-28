package skalman.ui.alarm

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import skalman.data.models.CalendarAlarm
import skalman.utils.dateUtils.toReadableString
import java.time.format.DateTimeFormatter

@Composable
fun AlarmCard(alarm: CalendarAlarm, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = alarm.title, style = MaterialTheme.typography.titleMedium)

            alarm.description?.let {
                Text(text = it, style = MaterialTheme.typography.bodyMedium)
            }

            Text(text = "Start: ${alarm.startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))}")

            if (alarm.preAlarmMinutes > 0) {
                Text(text = "Föralarm: ${alarm.preAlarmMinutes} min innan")
            }

            alarm.recurrenceRules?.let {
                Text(text = "Upprepning: ${it.toReadableString()}")
            }

            alarm.notes?.let {
                Text(text = "Anteckning: $it", style = MaterialTheme.typography.bodySmall)
            }

            Text(text = "Ljud: ${alarm.alarmSound}")
        }
    }
}
