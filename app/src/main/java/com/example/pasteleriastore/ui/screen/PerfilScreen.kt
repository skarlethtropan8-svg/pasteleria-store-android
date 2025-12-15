package com.example.pasteleriastore.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.pasteleriastore.R
import com.example.pasteleriastore.ui.components.BotonPastel
import com.example.pasteleriastore.ui.components.InformacionCard
import com.example.pasteleriastore.ui.components.TituloText
import com.example.pasteleriastore.viewmodel.PerfilViewModel



@Composable
fun PerfilScreen(navController: NavController, userNombre: String) {
    val viewModel: PerfilViewModel = viewModel()
    var usuario by remember { mutableStateOf<com.example.pasteleriastore.model.Usuario?>(null) }

    // Cargar datos del usuario
    LaunchedEffect(userNombre) {
        usuario = viewModel.cargarUsuario(userNombre)
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        //Imagen de fondo
        Image(
            painter = painterResource(id = R.drawable.logop),
            contentDescription = "Fondo pastel home",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.8f)
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp) ,
            verticalArrangement = Arrangement.Center,
        ) {
            //Título
            TituloText("Mi Perfil,")

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = userNombre,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White,
                letterSpacing = 1.sp
            )

            // Información del usuario
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    InformacionCard("Nombre de usuario", userNombre)
                    InformacionCard("Correo electrónico", usuario?.correo ?: "No especificado")
                    InformacionCard("Teléfono", usuario?.telefono ?: "No especificado")
                    InformacionCard("Dirección", usuario?.direccion ?: "No especificado")
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Botones
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BotonPastel(
                    texto = "Editar Perfil",
                    onClickAccion = {
                        navController.navigate("editarPerfil/${userNombre}")
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                BotonPastel(
                    texto = "Volver al Home",
                    onClickAccion = {
                        navController.popBackStack()
                        //navController.navigate("home")
                    }
                )
            }
        }
    }
}

