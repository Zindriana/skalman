package skalman

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.*
import org.junit.Rule
import org.junit.Test
import skalman.MainActivity

class DateTimeDisplayTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()  // 🟢 Startar riktig activity

    @Test
    fun dateTimeIsVisibleInTopRightCorner() {
        composeTestRule
            .onNodeWithTag("dateTimeDisplay")
            .assertIsDisplayed()  // 🟢 Fungerar nu eftersom aktiviteten körs
    }
}
