package skalman.ui.addalarm

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import skalman.data.models.CalendarAlarm
import skalman.utils.alarmUtils.AlarmScheduler
import skalman.viewmodel.CalendarViewModel
import java.time.LocalDateTime

@Composable
fun AddAlarmScreen(viewModel: CalendarViewModel) {
    val context = LocalContext.current
    val alarmScheduler = remember { AlarmScheduler(context) }

    // Form states
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var startTime by remember { mutableStateOf(LocalDateTime.now().plusMinutes(1)) } // placeholder
    var preAlarmMinutes by remember { mutableStateOf(5) }
    var colorTag by remember { mutableStateOf("blue") }
    var alarmSound by remember { mutableStateOf("default") }
    var notes by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Text("Lägg till nytt alarm", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Titel") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Beskrivning") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = notes,
            onValueChange = { notes = it },
            label = { Text("Anteckningar") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text("Föralarm: $preAlarmMinutes minuter innan")
        Slider(
            value = preAlarmMinutes.toFloat(),
            onValueChange = { preAlarmMinutes = it.toInt() },
            valueRange = 0f..60f,
            steps = 5
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val newAlarm = CalendarAlarm(
                title = title,
                description = description.ifBlank { null },
                startTime = startTime,
                preAlarmMinutes = preAlarmMinutes,
                recurrenceRules = null,
                ignoreRules = null,
                colorTag = colorTag,
                alarmSound = alarmSound,
                notes = notes.ifBlank { null }
            )

            viewModel.addAlarm(newAlarm)
            alarmScheduler.scheduleAlarm(newAlarm)
        }) {
            Text("Spara och schemalägg")
        }
    }
}
