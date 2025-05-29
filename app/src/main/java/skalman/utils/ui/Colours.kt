package skalman.utils.ui

import androidx.compose.ui.graphics.Color

fun colorFromTag(tag: String?): Color = when (tag?.lowercase()) {
    "grå" -> Color(0xFFBDBDBD)
    "blå" -> Color(0xFF2196F3)
    "grön" -> Color(0xFF4CAF50)
    "gul" -> Color(0xFFFFEB3B)
    "lila" -> Color(0xFF9C27B0)
    "röd" -> Color(0xFFF44336)
    else -> Color(0xFFBDBDBD)
}