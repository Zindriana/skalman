package skalman.ui.calendar

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import skalman.data.mock.getDummyDayWithAlarms
import skalman.ui.calendar.components.CalendarPeriodView
import skalman.ui.calendar.components.DateViewButton
import skalman.data.models.CalendarAlarm
import skalman.data.models.DayWithAlarms
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen() {
    var selectedPeriod by remember { mutableIntStateOf(7) }

    val dummyDayList = remember(selectedPeriod) {
        getDummyDayWithAlarms(selectedPeriod)
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Din kalender") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            DateViewButton(
                selectedView = selectedPeriod,
                onViewModeChange = { selectedPeriod = it }
            )

            CalendarPeriodView(
                period = selectedPeriod,
                data = dummyDayList
            )
        }
    }
}
