package skalman.ui.alarm

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import skalman.viewmodel.CalendarViewModel

@Composable
fun AddAlarmScreen(
    viewModel: CalendarViewModel,
    onBack: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { padding ->
        AlarmForm(
            modifier = Modifier.padding(padding),
            onSubmit = { viewModel.addAlarm(it) },
            onCancel = onBack
        )
    }
}
