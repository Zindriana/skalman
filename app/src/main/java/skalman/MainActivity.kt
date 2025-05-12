package skalman

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import skalman.skalman.ui.theme.SkalmanTheme
import skalman.ui.calendar.CalendarScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SkalmanTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    CalendarScreen()
                }
            }
        }
    }
}