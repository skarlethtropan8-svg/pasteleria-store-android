package com.example.pasteleriastore.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


@Composable
fun TituloText(
    titulo: String,
) {
    Text(
        text = titulo,
        style = MaterialTheme.typography.headlineLarge.copy(
            fontSize = 40.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Black,
            color = Color(0xFF6D4C41),
            letterSpacing = 1.5.sp
        )
    )
}


