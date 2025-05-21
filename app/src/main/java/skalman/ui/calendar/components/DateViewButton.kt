package skalman.ui.calendar.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DateViewButton(

    selectedView: Int = 7,
    onViewModeChange: (Int) -> Unit = {}
){
    val viewOptions = listOf(
        1 to "Dag",
        7 to "Vecka",
        30 to "Månad"
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ){
        viewOptions.forEach { (viewKey, label) ->
            val isSelected = selectedView == viewKey

            Button(
                onClick = { onViewModeChange(viewKey) },
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSelected) Color(0xFF4CAF50) else Color(0xFFBDBDBD),
                    contentColor = if (isSelected) Color.White else Color.Black
                )
            )
            {
                Text(text = label)
            }
        }
    }
}