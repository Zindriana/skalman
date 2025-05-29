package skalman.ui.alarm

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import skalman.data.models.RecurrenceRule

@Composable
fun RecurrenceRuleSection(
    recurrenceRule: RecurrenceRule?,
    onRuleChange: (RecurrenceRule?) -> Unit
) {
    val weekdays = listOf(
        1 to "Mån", 2 to "Tis", 3 to "Ons", 4 to "Tor",
        5 to "Fre", 6 to "Lör", 7 to "Sön"
    )

    var selectedType by remember {
        mutableStateOf(
            when (recurrenceRule) {
                is RecurrenceRule.IntervalDays -> "Var X dag"
                is RecurrenceRule.Weekly -> "Varje vecka"
                else -> "Ingen"
            }
        )
    }

    var interval by remember {
        mutableStateOf((recurrenceRule as? RecurrenceRule.IntervalDays)?.everyXDays?.toString() ?: "1")
    }

    var selectedWeekdays by remember {
        mutableStateOf((recurrenceRule as? RecurrenceRule.Weekly)?.daysOfWeek ?: emptySet())
    }

    var selectedOddEven by remember {
        mutableStateOf((recurrenceRule as? RecurrenceRule.Weekly)?.moduloWeek)
    }

    Column {
        Text("Upprepning", style = MaterialTheme.typography.titleMedium)

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            listOf("Ingen", "Var X dag", "Varje vecka").forEach {
                FilterChip(
                    selected = selectedType == it,
                    onClick = {
                        selectedType = it
                        when (it) {
                            "Ingen" -> onRuleChange(null)
                            "Var X dag" -> onRuleChange(RecurrenceRule.IntervalDays(interval.toIntOrNull() ?: 1))
                            "Varje vecka" -> onRuleChange(RecurrenceRule.Weekly(selectedWeekdays, selectedOddEven))
                        }
                    },
                    label = { Text(it) }
                )
            }
        }

        if (selectedType == "Var X dag") {
            OutlinedTextField(
                value = interval,
                onValueChange = {
                    interval = it.filter { c -> c.isDigit() }
                    val x = interval.toIntOrNull() ?: 1
                    onRuleChange(RecurrenceRule.IntervalDays(x))
                },
                label = { Text("Var X:e dag") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        if (selectedType == "Varje vecka") {
            Text("Veckodagar:")
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                weekdays.forEach { (num, label) ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Checkbox(
                            checked = selectedWeekdays.contains(num),
                            onCheckedChange = {
                                selectedWeekdays = if (it) selectedWeekdays + num else selectedWeekdays - num
                                onRuleChange(RecurrenceRule.Weekly(selectedWeekdays, selectedOddEven))
                            }
                        )
                        Text(label)
                    }
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterChip(selected = selectedOddEven == null, onClick = {
                    selectedOddEven = null
                    onRuleChange(RecurrenceRule.Weekly(selectedWeekdays, null))
                }, label = { Text("Alla veckor") })

                FilterChip(selected = selectedOddEven == 0, onClick = {
                    selectedOddEven = 0
                    onRuleChange(RecurrenceRule.Weekly(selectedWeekdays, 0))
                }, label = { Text("Jämna") })

                FilterChip(selected = selectedOddEven == 1, onClick = {
                    selectedOddEven = 1
                    onRuleChange(RecurrenceRule.Weekly(selectedWeekdays, 1))
                }, label = { Text("Ojämna") })
            }
        }
    }
}
