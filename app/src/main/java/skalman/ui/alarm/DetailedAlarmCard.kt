package skalman.ui.alarm

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import skalman.data.models.CalendarAlarm
import skalman.utils.dateUtils.toReadableString
import java.time.format.DateTimeFormatter

@Composable
fun DetailedAlarmCard(
    alarm: CalendarAlarm?,
    onDelete: (CalendarAlarm) -> Unit = {},
    onUpdate: (CalendarAlarm) -> Unit = {},
) {
    if (alarm == null) return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text(text = alarm.title, style = MaterialTheme.typography.headlineSmall)

        alarm.description?.let {
            Text(text = it, style = MaterialTheme.typography.bodyLarge)
        }

        Text(
            text = "Starttid: ${alarm.startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))}",
            style = MaterialTheme.typography.bodyMedium
        )

        if (alarm.preAlarmMinutes > 0) {
            Text(text = "Föralarm: ${alarm.preAlarmMinutes} min innan")
        }

        alarm.recurrenceRules?.let {
            Text(text = "Upprepning: ${it.toReadableString()}")
        }

        alarm.ignoreRules?.takeIf { it.isNotEmpty() }?.let { rules ->
            Text(text = "Undantagsregler:", style = MaterialTheme.typography.titleSmall)
            rules.forEach { rule ->
                Text(text = rule.toReadableString(), style = MaterialTheme.typography.bodySmall)
            }
        }

        alarm.colorTag?.let {
            Text(text = "Färg: $it")
        }

        Text(text = "Ljud: ${alarm.alarmSound}")

        alarm.notes?.let {
            Text(text = "Anteckningar: $it")
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { onUpdate(alarm) }) {
                Text("Uppdatera")
            }

            Button(onClick = { onDelete(alarm) }, colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.errorContainer
            )) {
                Text("Ta bort")
            }

        }
    }
}
