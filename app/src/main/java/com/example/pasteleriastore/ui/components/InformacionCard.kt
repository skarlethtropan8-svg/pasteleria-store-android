package com.example.pasteleriastore.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun InformacionCard(titulo: String, valor: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = titulo,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF6D4C41), //  Marrón pastel suave
            fontSize = 22.sp
        )

        Text(
            text = valor,
            fontSize = 18.sp,
            color = Color(0xFFC9ADA7), //  Rosa viejo pastel
            modifier = Modifier.padding(top = 4.dp)
        )

        Divider(
            modifier = Modifier.padding(vertical = 12.dp),
            color = Color(0xFFD8C3A5).copy(alpha = 0.5f) // Divider nude cálido
        )
    }
}
