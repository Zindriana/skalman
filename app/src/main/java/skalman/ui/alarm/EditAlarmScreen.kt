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
import kotlinx.coroutines.launch
import skalman.data.models.CalendarAlarm
import skalman.viewmodel.CalendarViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditAlarmScreen(
    viewModel: CalendarViewModel,
    alarmToEdit: CalendarAlarm,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    // Fält som initieras från alarmToEdit
    var title by remember { mutableStateOf(alarmToEdit.title) }
    var description by remember { mutableStateOf(alarmToEdit.description ?: "") }
    var notes by remember { mutableStateOf(alarmToEdit.notes ?: "") }
    var preAlarmMinutes by remember { mutableStateOf(alarmToEdit.preAlarmMinutes.toString()) }
    var colorTag by remember { mutableStateOf(alarmToEdit.colorTag ?: "blue") }
    var alarmSound by remember { mutableStateOf(alarmToEdit.alarmSound) }

    var startDate by remember { mutableStateOf(alarmToEdit.startTime.toLocalDate()) }
    var startTime by remember { mutableStateOf(alarmToEdit.startTime.toLocalTime()) }

    val fullStartTime = remember(startDate, startTime) {
        LocalDateTime.of(startDate, startTime)
    }

    val colorOptions = listOf("blue", "green", "red", "yellow", "purple")
    val soundOptions = listOf("default")

    var colorExpanded by remember { mutableStateOf(false) }
    var soundExpanded by remember { mutableStateOf(false) }

    Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(padding)
        ) {
            Text("Redigera alarm", style = MaterialTheme.typography.titleLarge)
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

            // Datum & tid
            Box(modifier = Modifier.fillMaxWidth().clickable {
                DatePickerDialog(
                    context,
                    { _, year, month, dayOfMonth ->
                        startDate = LocalDate.of(year, month + 1, dayOfMonth)
                    },
                    startDate.year,
                    startDate.monthValue - 1,
                    startDate.dayOfMonth
                ).show()
            }) {
                OutlinedTextField(
                    value = startDate.format(DateTimeFormatter.ISO_LOCAL_DATE),
                    onValueChange = {},
                    label = { Text("Datum") },
                    enabled = false,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Box(modifier = Modifier.fillMaxWidth().clickable {
                TimePickerDialog(
                    context,
                    { _, hour, minute ->
                        startTime = LocalTime.of(hour, minute)
                    },
                    startTime.hour,
                    startTime.minute,
                    true
                ).show()
            }) {
                OutlinedTextField(
                    value = startTime.format(DateTimeFormatter.ofPattern("HH:mm")),
                    onValueChange = {},
                    label = { Text("Tid") },
                    enabled = false,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = preAlarmMinutes,
                onValueChange = { preAlarmMinutes = it.filter { c -> c.isDigit() } },
                label = { Text("Föralarm (minuter)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Färg
            ExposedDropdownMenuBox(
                expanded = colorExpanded,
                onExpandedChange = { colorExpanded = !colorExpanded }
            ) {
                OutlinedTextField(
                    value = colorTag,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Färg") },
                    modifier = Modifier.fillMaxWidth()
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

            // Ljud
            ExposedDropdownMenuBox(
                expanded = soundExpanded,
                onExpandedChange = { soundExpanded = !soundExpanded }
            ) {
                OutlinedTextField(
                    value = alarmSound,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Ljud") },
                    modifier = Modifier.fillMaxWidth()
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
                    if (title.isBlank()) {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Titel krävs för att spara ändringar")
                        }
                        return@Button
                    }

                    val updatedAlarm = alarmToEdit.copy(
                        title = title,
                        description = description.ifBlank { null },
                        notes = notes.ifBlank { null },
                        preAlarmMinutes = preAlarmMinutes.toIntOrNull() ?: 0,
                        startTime = fullStartTime,
                        colorTag = colorTag,
                        alarmSound = alarmSound
                    )

                    viewModel.updateAlarm(updatedAlarm)
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Ändringar sparade")
                    }
                    onBack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Spara ändringar")
            }

            OutlinedButton(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Avbryt")
            }
        }
    }
}
