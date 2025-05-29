package skalman.ui.alarm

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import skalman.utils.alarmUtils.AlarmPermissionHelper
import skalman.viewmodel.CalendarViewModel

@Composable
fun AddAlarmScreen(
    viewModel: CalendarViewModel,
    onBack: () -> Unit
) {
    val context = LocalContext.current

    // 🔔 Runtime permission för notifikationer (Android 13+)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        val permission = Manifest.permission.POST_NOTIFICATIONS
        val launcher = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { granted ->
            // Ignoreras för nu – kan loggas eller visas
        }

        LaunchedEffect(Unit) {
            val notGranted = ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED
            if (notGranted) launcher.launch(permission)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { padding ->
        AlarmForm(
            modifier = Modifier.padding(padding),
            onSubmit = { alarm ->
                // ✅ Kontrollera tillstånd för exakta alarm innan schemaläggning
                if (AlarmPermissionHelper.checkAndRequestExactAlarmPermission(context)) {
                    viewModel.addAlarm(alarm)
                    onBack()
                }
            },
            onCancel = onBack
        )
    }
}
