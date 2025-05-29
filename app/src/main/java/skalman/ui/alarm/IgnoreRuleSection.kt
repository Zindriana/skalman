package skalman.ui.alarm

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import skalman.data.models.IgnoreRule
import java.time.LocalDate

@Composable
fun IgnoreRuleSection(
    initialRules: List<IgnoreRule>,
    onRuleChange: (List<IgnoreRule>) -> Unit
) {
    val weekdays = listOf(
        1 to "Mån", 2 to "Tis", 3 to "Ons", 4 to "Tor",
        5 to "Fre", 6 to "Lör", 7 to "Sön"
    )

    var ignoredWeekdays by remember {
        mutableStateOf(initialRules.filterIsInstance<IgnoreRule.IgnoreWeekdays>().firstOrNull()?.days ?: emptySet())
    }

    var ignoredOddEven by remember {
        mutableStateOf(initialRules.filterIsInstance<IgnoreRule.IgnoreWeekdays>().firstOrNull()?.moduloWeek)
    }

    var ignoredDates by remember {
        mutableStateOf(initialRules.filterIsInstance<IgnoreRule.IgnoreDates>().firstOrNull()?.dates ?: emptySet())
    }

    Column {
        Text("Undantag", style = MaterialTheme.typography.titleMedium)

        Text("Veckodagar att ignorera:")

        // ✅ Uppdaterad rad med veckodags-checkboxar
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
                            onRuleChange(buildList {
                                add(IgnoreRule.IgnoreWeekdays(ignoredWeekdays, ignoredOddEven))
                                if (ignoredDates.isNotEmpty()) add(IgnoreRule.IgnoreDates(ignoredDates))
                            })
                        }
                    )
                    Text(label)
                }
            }
        }

        Row {
            FilterChip(selected = ignoredOddEven == null, onClick = {
                ignoredOddEven = null
                onRuleChange(buildList {
                    add(IgnoreRule.IgnoreWeekdays(ignoredWeekdays, null))
                    if (ignoredDates.isNotEmpty()) add(IgnoreRule.IgnoreDates(ignoredDates))
                })
            }, label = { Text("Alla veckor") })

            FilterChip(selected = ignoredOddEven == 0, onClick = {
                ignoredOddEven = 0
                onRuleChange(buildList {
                    add(IgnoreRule.IgnoreWeekdays(ignoredWeekdays, 0))
                    if (ignoredDates.isNotEmpty()) add(IgnoreRule.IgnoreDates(ignoredDates))
                })
            }, label = { Text("Jämna") })

            FilterChip(selected = ignoredOddEven == 1, onClick = {
                ignoredOddEven = 1
                onRuleChange(buildList {
                    add(IgnoreRule.IgnoreWeekdays(ignoredWeekdays, 1))
                    if (ignoredDates.isNotEmpty()) add(IgnoreRule.IgnoreDates(ignoredDates))
                })
            }, label = { Text("Ojämna") })
        }

        Spacer(Modifier.height(8.dp))

        Text("Datum att ignorera:")
        Button(onClick = {
            val today = LocalDate.now()
            ignoredDates = ignoredDates + today
            onRuleChange(buildList {
                add(IgnoreRule.IgnoreWeekdays(ignoredWeekdays, ignoredOddEven))
                add(IgnoreRule.IgnoreDates(ignoredDates))
            })
        }) {
            Text("Lägg till dagens datum (exempel)")
        }

        Text(ignoredDates.sorted().joinToString(", ") { it.toString() })
    }
}
