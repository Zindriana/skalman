package skalman.ui.alarm

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import skalman.data.models.IgnoreRule
import java.time.LocalDate
import skalman.utils.ui.weekdays

@Composable
fun IgnoreRuleSection(
    initialRules: List<IgnoreRule>,
    onRuleChange: (List<IgnoreRule>) -> Unit
) {

    var ignoredWeekdays by remember {
        mutableStateOf(initialRules.filterIsInstance<IgnoreRule.IgnoreWeekdays>().firstOrNull()?.days ?: emptySet())
    }

    var ignoredOddEven by remember {
        mutableStateOf(initialRules.filterIsInstance<IgnoreRule.IgnoreWeekdays>().firstOrNull()?.moduloWeek)
    }

    // ignoreDates är inget som används i nuvarande version,
    // men funktion för det kommer läggas till i en senare version
    var ignoredDates by remember { mutableStateOf(emptySet<LocalDate>()) }

    Column {
        Text("Undantag", style = MaterialTheme.typography.titleMedium)

        Text("Veckodagar att ignorera:")

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            weekdays.forEach { (num, label) ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Checkbox(
                        checked = ignoredWeekdays.contains(num),
                        onCheckedChange = {
                            ignoredWeekdays = if (it) ignoredWeekdays + num else ignoredWeekdays - num
                            onRuleChange(listOf(IgnoreRule.IgnoreWeekdays(ignoredWeekdays, ignoredOddEven)))
                        }
                    )
                    Text(label)
                }
            }
        }

        Row {
            FilterChip(
                selected = ignoredOddEven == null,
                onClick = {
                    ignoredOddEven = null
                    onRuleChange(listOf(IgnoreRule.IgnoreWeekdays(ignoredWeekdays, null)))
                },
                label = { Text("Alla veckor") }
            )

            Spacer(modifier = Modifier.width(8.dp))

            FilterChip(
                selected = ignoredOddEven == 0,
                onClick = {
                    ignoredOddEven = 0
                    onRuleChange(listOf(IgnoreRule.IgnoreWeekdays(ignoredWeekdays, 0)))
                },
                label = { Text("Jämna veckor") }
            )

            Spacer(modifier = Modifier.width(8.dp))

            FilterChip(
                selected = ignoredOddEven == 1,
                onClick = {
                    ignoredOddEven = 1
                    onRuleChange(listOf(IgnoreRule.IgnoreWeekdays(ignoredWeekdays, 1)))
                },
                label = { Text("Ojämna veckor") }
            )
        }

        Spacer(Modifier.height(8.dp))

        Text("Datum att ignorera:")
        Button(onClick = {
            //tom knapp, ska läggas in en multiple datepicker här i en framtida version
        }) {
            Text("Placeholder för datepicker ")
        }
    }
}
