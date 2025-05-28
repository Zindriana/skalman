package skalman.utils.alarmUtils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.getStringExtra("title") ?: "Alarm"
        Toast.makeText(context, "Alarm: $title", Toast.LENGTH_LONG).show()

    }
}
