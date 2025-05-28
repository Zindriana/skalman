package skalman.ui.alarm

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import skalman.data.models.CalendarAlarm
import skalman.viewmodel.CalendarViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAlarmScreen(viewModel: CalendarViewModel) {
    val context = LocalContext.current

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var preAlarmMinutes by remember { mutableStateOf("5") }
    var colorTag by remember { mutableStateOf("blue") }
    var alarmSound by remember { mutableStateOf("default") }

    var startDate by remember { mutableStateOf(LocalDate.now()) }
    var startTime by remember { mutableStateOf(LocalTime.now().plusMinutes(1)) }

    val fullStartTime = remember(startDate, startTime) {
        LocalDateTime.of(startDate, startTime)
    }

    val colorOptions = listOf("blue", "green", "red", "yellow", "purple")
    var colorExpanded by remember { mutableStateOf(false) }

    val soundOptions = listOf("default")
    var soundExpanded by remember { mutableStateOf(false) }

    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                startDate = LocalDate.of(year, month + 1, dayOfMonth)
            },
            startDate.year,
            startDate.monthValue - 1,
            startDate.dayOfMonth
        )
    }

    val timePickerDialog = remember {
        TimePickerDialog(
            context,
            { _, hour, minute ->
                startTime = LocalTime.of(hour, minute)
            },
            startTime.hour,
            startTime.minute,
            true
        )
    }

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

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = startDate.format(DateTimeFormatter.ISO_LOCAL_DATE),
            onValueChange = {},
            readOnly = true,
            label = { Text("Datum") },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { datePickerDialog.show() }
        )

        OutlinedTextField(
            value = startTime.format(DateTimeFormatter.ofPattern("HH:mm")),
            onValueChange = {},
            readOnly = true,
            label = { Text("Tid") },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { timePickerDialog.show() }
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = preAlarmMinutes,
            onValueChange = { preAlarmMinutes = it.filter { c -> c.isDigit() } },
            label = { Text("Föralarm (minuter)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        ExposedDropdownMenuBox(
            expanded = colorExpanded,
            onExpandedChange = { colorExpanded = !colorExpanded }
        ) {
            OutlinedTextField(
                value = colorTag,
                onValueChange = {},
                readOnly = true,
                label = { Text("Färg") },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = colorExpanded,
                onDismissRequest = { colorExpanded = false }
            ) {
                colorOptions.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            colorTag = option
                            colorExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        ExposedDropdownMenuBox(
            expanded = soundExpanded,
            onExpandedChange = { soundExpanded = !soundExpanded }
        ) {
            OutlinedTextField(
                value = alarmSound,
                onValueChange = {},
                readOnly = true,
                label = { Text("Ljud") },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = soundExpanded,
                onDismissRequest = { soundExpanded = false }
            ) {
                soundOptions.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            alarmSound = option
                            soundExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val newAlarm = CalendarAlarm(
                    title = title,
                    description = description.ifBlank { null },
                    startTime = fullStartTime,
                    preAlarmMinutes = preAlarmMinutes.toIntOrNull() ?: 0,
                    recurrenceRules = null,
                    ignoreRules = null,
                    colorTag = colorTag,
                    alarmSound = alarmSound,
                    notes = notes.ifBlank { null }
                )
                viewModel.addAlarm(newAlarm)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Spara och schemalägg")
        }
    }
}
