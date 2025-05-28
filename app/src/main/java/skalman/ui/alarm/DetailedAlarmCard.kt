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
    onBack: () -> Unit = {}
) {
    if (alarm == null) return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Titel
        Text(text = alarm.title, style = MaterialTheme.typography.headlineSmall)

        // Beskrivning
        alarm.description?.let {
            Text(text = it, style = MaterialTheme.typography.bodyLarge)
        }

        // Starttid
        Text(
            text = "Starttid: ${alarm.startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))}",
            style = MaterialTheme.typography.bodyMedium
        )

        // Föralarm
        if (alarm.preAlarmMinutes > 0) {
            Text(text = "Föralarm: ${alarm.preAlarmMinutes} min innan")
        }

        // Upprepning
        alarm.recurrenceRules?.let {
            Text(text = "Upprepning: ${it.toReadableString()}")
        }

        // Ignorera-regler
        alarm.ignoreRules?.takeIf { it.isNotEmpty() }?.let { rules ->
            Text(text = "Undantagsregler:", style = MaterialTheme.typography.titleSmall)
            rules.forEach { rule ->
                Text(text = rule.toReadableString(), style = MaterialTheme.typography.bodySmall)
            }
        }


        // Färg
        alarm.colorTag?.let {
            Text(text = "Färg: $it")
        }

        // Ljud
        Text(text = "Ljud: ${alarm.alarmSound}")

        // Anteckningar
        alarm.notes?.let {
            Text(text = "Anteckningar: $it")
        }

        // Actionknappar
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { onUpdate(alarm) }) {
                Text("Uppdatera")
            }

            Button(onClick = { onDelete(alarm) }, colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.errorContainer
            )) {
                Text("Ta bort")
            }

            OutlinedButton(onClick = onBack) {
                Text("Tillbaka")
            }
        }
    }
}
