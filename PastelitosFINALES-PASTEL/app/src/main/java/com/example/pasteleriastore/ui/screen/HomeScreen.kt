package com.example.pasteleriastore.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pasteleriastore.R
import com.example.pasteleriastore.ui.components.BotonPastel
import com.example.pasteleriastore.ui.components.TituloText

@Composable
//esta pagina es para mostrar donde estan los productos

fun HomeScreen(navController: NavController, userNombre: String? = null){
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Image(
            painter = painterResource(id = R.drawable.logopasteleria),
            contentDescription = "Fondo Pasteleria",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.TopCenter)
                .alpha(0.9f)
        )
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {

        //Título
        TituloText("Qué gusto verte")

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = userNombre ?: "Usuario",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFFC9ADA7),
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(30.dp))
        //Primera fila
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier.weight(1f)
            ) {
                BotonPastel(
                    texto = "Crear Producto",
                    onClickAccion = {
                        navController.navigate("registroProducto")
                    }
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Box(
                modifier = Modifier.weight(1f)
            ) {
                BotonPastel(
                    texto = "Ver Productos",
                    onClickAccion = {
                        navController.navigate("productos")
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        //Segunda fila
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier.weight(1f)
            ) {
                BotonPastel(
                    texto = "Mi Perfil",
                    onClickAccion = {
                        navController.navigate("perfil/${userNombre ?: ""}")
                    }
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Box(
                modifier = Modifier.weight(1f)
            ) {
                BotonPastel(
                    texto = "Mi Carrito",
                    onClickAccion = {
                        // Pasa el nombre del usuario actual al carrito
                        val usuarioActual = userNombre ?: "cliente"
                        navController.navigate("carrito/$usuarioActual")

                    }
                )
            }
        }

        //Boton Cerrar Sesion
        Spacer(modifier = Modifier.height(40.dp))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            BotonPastel(
                texto = "Cerrar Sesión",
                onClickAccion = {
                    navController.navigate("login")
                }
            )
        }
    }
}