package skalman.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlinx.coroutines.delay

@Composable
fun DateTimeDisplay(modifier: Modifier = Modifier) {
    // State som håller aktuell tid
    var current by remember { mutableStateOf(LocalDateTime.now()) }

    // Uppdaterar tiden varje sekund
    LaunchedEffect(Unit) {
        while (true) {
            current = LocalDateTime.now()
            delay(1000)
        }
    }

    // Formatterar tid som: tisdag, v.20, 13 maj 14:45:00
    val formatter = DateTimeFormatter.ofPattern("EEEE, 'v.'w, dd MMM HH:mm:ss", Locale("sv"))
    val text = current.format(formatter)

    // Visar tiden i UI
    Text(
        text = text,
        modifier = modifier
            .testTag("dateTimeDisplay")
            .padding(8.dp),
        style = MaterialTheme.typography.bodyMedium,
        textAlign = TextAlign.End
    )
}