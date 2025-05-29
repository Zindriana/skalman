package skalman.ui.alarm

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import skalman.data.models.CalendarAlarm
import skalman.viewmodel.CalendarViewModel

@Composable
fun EditAlarmScreen(
    viewModel: CalendarViewModel,
    alarmToEdit: CalendarAlarm,
    onBack: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { padding ->
        AlarmForm(
            modifier = Modifier.padding(padding),
            initialAlarm = alarmToEdit,
            onSubmit = {
                viewModel.updateAlarm(it)
                onBack()
            },
            onCancel = onBack
        )
    }
}
