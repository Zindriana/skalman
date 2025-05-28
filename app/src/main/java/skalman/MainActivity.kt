package skalman

import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.room.Room
import skalman.data.db.CalendarDatabase
import skalman.data.repo.AlarmRepository
import skalman.ui.main.MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Create Room database instance
        val db = Room.databaseBuilder(
            applicationContext,
            CalendarDatabase::class.java,
            "skalman-db"
        ).build()

        // Create repository instance
        val repository = AlarmRepository(db.alarmDao())

        hideSystemUI()

        // Launch Compose UI with repository passed in
        setContent {
            MainScreen(repository)
        }
    }

    private fun hideSystemUI() {
        window.decorView.post {
            window.insetsController?.let { controller ->
                controller.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                controller.systemBarsBehavior =
                    WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) hideSystemUI()
    }
}
