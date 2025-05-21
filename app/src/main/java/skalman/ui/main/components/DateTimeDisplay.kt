package skalman.ui.main.components

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
    var current by remember { mutableStateOf(LocalDateTime.now()) }

    LaunchedEffect(Unit) {
        while (true) {
            current = LocalDateTime.now()
            delay(1000)
        }
    }

    val formatter = DateTimeFormatter.ofPattern("EEEE, 'v.'w, dd MMM HH:mm:ss", Locale("sv"))
    val text = current.format(formatter)

    Text(
        text = text,
        modifier = modifier
            .testTag("dateTimeDisplay")
            .padding(8.dp),
        style = MaterialTheme.typography.bodyMedium,
        textAlign = TextAlign.End
    )
}