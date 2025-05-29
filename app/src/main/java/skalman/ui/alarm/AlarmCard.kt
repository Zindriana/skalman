package skalman.ui.alarm

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import skalman.data.models.CalendarAlarm
import skalman.utils.ui.colorFromTag
import java.time.format.DateTimeFormatter

@Composable
fun AlarmCard(alarm: CalendarAlarm, onClick: () -> Unit) {
    val color = colorFromTag(alarm.colorTag)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        // 👇 Gör raden lika hög som dess innehåll (t.ex. Column)
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min) // Gör att Box kan använda fillMaxHeight()
        ) {
            // 👇 Färgindikator till vänster
            Box(
                modifier = Modifier
                    .width(12.dp)
                    .fillMaxHeight()
                    .background(color) // Använd färgen från tag
            )

            // 👇 Innehållet i kortet
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(text = alarm.title, style = MaterialTheme.typography.titleMedium)

                alarm.description?.let {
                    Text(text = it, style = MaterialTheme.typography.bodyMedium)
                }

                Text(
                    text = "Start: ${alarm.startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

