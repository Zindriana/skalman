package skalman.ui.alarm.screens

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import skalman.ui.alarm.AlarmForm
import skalman.utils.alarmUtils.AlarmPermissionHelper
import skalman.viewmodel.CalendarViewModel

@Composable
fun AddAlarmScreen(
    viewModel: CalendarViewModel,
    onBack: () -> Unit
) {
    val context = LocalContext.current

    //todo refaktorera och flytta ut delar av launcher och launched effect till utils
    val permission = Manifest.permission.POST_NOTIFICATIONS
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted -> }

    LaunchedEffect(Unit) {
        val notGranted = ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED
        if (notGranted) launcher.launch(permission)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { padding ->
        AlarmForm(
            modifier = Modifier.padding(padding),
            onSubmit = { alarm ->
                if (AlarmPermissionHelper.checkAndRequestExactAlarmPermission(context)) {
                    viewModel.addAlarm(alarm)
                    onBack()
                }
            },
            onBack = onBack
        )
    }
}
