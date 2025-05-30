package skalman.ui.alarm

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import skalman.data.models.CalendarAlarm
import skalman.utils.DropdownSelector
import skalman.utils.dateUtils.DateTimePicker
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Composable
fun AlarmForm(
    modifier: Modifier = Modifier,
    initialAlarm: CalendarAlarm? = null,
    onSubmit: (CalendarAlarm) -> Unit,
    onBack: () -> Unit
) {
    var title by remember { mutableStateOf(initialAlarm?.title ?: "") }
    var description by remember { mutableStateOf(initialAlarm?.description ?: "") }
    var notes by remember { mutableStateOf(initialAlarm?.notes ?: "") }
    var preAlarmMinutes by remember { mutableStateOf(initialAlarm?.preAlarmMinutes?.toString() ?: "5") }
    var colorTag by remember { mutableStateOf(initialAlarm?.colorTag ?: "grå") }
    var alarmSound by remember { mutableStateOf(initialAlarm?.alarmSound ?: "default") }

    var startDate by remember { mutableStateOf(initialAlarm?.startTime?.toLocalDate() ?: LocalDate.now()) }
    var startTime by remember { mutableStateOf(initialAlarm?.startTime?.toLocalTime() ?: LocalTime.now().plusMinutes(1)) }
    val fullStartTime = remember(startDate, startTime) {
        LocalDateTime.of(startDate, startTime)
    }

    var recurrenceRule by remember { mutableStateOf(initialAlarm?.recurrenceRules) }
    var ignoreRules by remember { mutableStateOf(initialAlarm?.ignoreRules ?: emptyList()) }

    val colorOptions = listOf("blå", "grå", "grön", "gul", "lila", "röd")
    val soundOptions = listOf("default")

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item { Text("Alarm", style = MaterialTheme.typography.titleLarge) }

        item {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Titel") },
                modifier = Modifier.fillMaxWidth(),
                isError = title.isEmpty(),
                supportingText = { Text("Obligatoriskt fält") },
            )
        }

        item {
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Beskrivning") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text("Anteckningar") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            DateTimePicker(
                date = startDate,
                time = startTime,
                onDateChange = { startDate = it },
                onTimeChange = { startTime = it }
            )
        }

        item {
            OutlinedTextField(
                value = preAlarmMinutes,
                onValueChange = { preAlarmMinutes = it.filter { c -> c.isDigit() } },
                label = { Text("Föralarm (minuter)") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            RecurrenceRuleSection(
                recurrenceRule = recurrenceRule,
                onRuleChange = { recurrenceRule = it }
            )
        }

        //todo lägga in en kontroll så att IgnoreRuleSection bara är synlig ifall användaren
        //todo har valt någon recurrence
        item {
            IgnoreRuleSection(
                initialRules = ignoreRules,
                onRuleChange = { ignoreRules = it }
            )
        }

        item {
            DropdownSelector("Färg", colorTag, colorOptions) { colorTag = it }
        }

        item {
            DropdownSelector("Ljud", alarmSound, soundOptions) { alarmSound = it }
        }

        item {
            Button(
                onClick = {
                    //todo lämna ett meddelande till användare som ber användare att fylla i titel,
                    //todo exempelvis med hjälp av en snackbar
                    if (title.isBlank()) return@Button

                    val alarm = (initialAlarm ?: CalendarAlarm(startTime = fullStartTime)).copy(
                        title = title,
                        description = description.ifBlank { null },
                        notes = notes.ifBlank { null },
                        preAlarmMinutes = preAlarmMinutes.toIntOrNull() ?: 0,
                        startTime = fullStartTime,
                        recurrenceRules = recurrenceRule,
                        ignoreRules = ignoreRules,
                        colorTag = colorTag,
                        alarmSound = alarmSound
                    )
                    onSubmit(alarm)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Spara alarm")
            }
        }

        item {
            OutlinedButton(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Avbryt")
            }
        }
    }
}
