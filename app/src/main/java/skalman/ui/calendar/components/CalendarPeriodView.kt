package skalman.ui.calendar.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import skalman.data.models.DayWithAlarms
import skalman.ui.alarm.AlarmCard
import java.time.LocalDate

@Composable
fun CalendarPeriodView(period: Int, data: List<DayWithAlarms>) {
    val today = LocalDate.now()
    val endDate = today.plusDays(period.toLong())

    val visibleDays = data.filter { it.date in today..endDate }

    if (visibleDays.all { it.alarms.isEmpty() }) {
        Text(
            text = "Inga aktiviteter i vald period",
            modifier = Modifier.padding(16.dp)
        )
    } else {
        LazyColumn {
            visibleDays.forEach { day ->
                if (day.alarms.isNotEmpty()) {
                    item {
                        Column(modifier = Modifier.padding(vertical = 8.dp)) {
                            Text(
                                text = day.date.toString(),
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(start = 8.dp, bottom = 4.dp)
                            )
                            day.alarms.forEach { alarm ->
                                AlarmCard(alarm)
                            }
                        }
                    }
                }
            }
        }
    }
}
