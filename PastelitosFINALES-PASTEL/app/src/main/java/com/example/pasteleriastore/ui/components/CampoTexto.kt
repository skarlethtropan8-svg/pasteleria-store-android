package com.example.pasteleriastore.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.layout.Column

@Composable
fun CampoTexto(
    valor: String,
    onValorCambio: (String) -> Unit,
    etiqueta: String,
    esError: Boolean = false,
    mensajeError: String = "",
    visualTransformation: VisualTransformation = VisualTransformation.None,
    modifier: Modifier = Modifier
) {

    val colorTexto by animateColorAsState(
        targetValue = if (esError) Color(0xFFB56576) else Color(0xFF6D4C41), //  Marrón pastel o rojo pastel
        animationSpec = tween(durationMillis = 300),
        label = "colorTexto"
    )

    Column(modifier = modifier) {

        OutlinedTextField(
            value = valor,
            onValueChange = onValorCambio,
            label = {
                Text(
                    text = etiqueta,
                    color = Color(0xFFC9ADA7), //  Rosa viejo pastel
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            },
            textStyle = androidx.compose.ui.text.TextStyle(
                color = colorTexto //  Texto animado pastel
            ),
            isError = esError,
            visualTransformation = visualTransformation,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color(0xFFC9ADA7),   //  Rosa viejo pastel
                unfocusedIndicatorColor = Color(0xFFD8C3A5), //  Nude cálido
                cursorColor = Color(0xFF6D4C41),             //  Marrón pastel
                focusedLabelColor = Color(0xFFC9ADA7),
                unfocusedLabelColor = Color(0xFFD8C3A5)
            ),
            modifier = Modifier.fillMaxWidth()
        )

        AnimatedVisibility(
            visible = esError && mensajeError.isNotEmpty(),
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Text(
                text = mensajeError,
                color = Color(0xFFB56576), //  Rojo pastel suave
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}
