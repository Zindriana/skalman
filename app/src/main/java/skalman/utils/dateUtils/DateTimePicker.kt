package skalman.utils.dateUtils

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun DateTimePicker(
    date: LocalDate,
    time: LocalTime,
    onDateChange: (LocalDate) -> Unit,
    onTimeChange: (LocalTime) -> Unit
) {
    val context = LocalContext.current
    val dateFormatter = remember { DateTimeFormatter.ISO_LOCAL_DATE }
    val timeFormatter = remember { DateTimeFormatter.ofPattern("HH:mm") }

    Box(modifier = Modifier.fillMaxWidth().clickable {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                onDateChange(LocalDate.of(year, month + 1, dayOfMonth))
            },
            date.year,
            date.monthValue - 1,
            date.dayOfMonth
        ).show()
    }) {
        OutlinedTextField(
            value = date.format(dateFormatter),
            onValueChange = {},
            label = { Text("Datum") },
            enabled = false,
            modifier = Modifier.fillMaxWidth()
        )
    }

    Spacer(modifier = Modifier.height(8.dp))

    Box(modifier = Modifier.fillMaxWidth().clickable {
        TimePickerDialog(
            context,
            { _, hour, minute ->
                onTimeChange(LocalTime.of(hour, minute))
            },
            time.hour,
            time.minute,
            true
        ).show()
    }) {
        OutlinedTextField(
            value = time.format(timeFormatter),
            onValueChange = {},
            label = { Text("Tid") },
            enabled = false,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
