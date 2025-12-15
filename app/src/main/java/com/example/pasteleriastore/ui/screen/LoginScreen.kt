package com.example.pasteleriastore.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.pasteleriastore.R
import com.example.pasteleriastore.ui.components.BotonPastel
import com.example.pasteleriastore.ui.components.CampoTexto
import com.example.pasteleriastore.ui.components.TituloText
import com.example.pasteleriastore.viewmodel.LoginViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun LoginScreen(navController: NavController){
    var usuario by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    val viewModel: LoginViewModel = viewModel()



    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
            .background(Color(0xFFF8DECF))
    ) {
        //Imagen de fondo
        Image(
            painter = painterResource(id = R.drawable.logoprincipal),
            contentDescription = "Fondo login Inicio",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.25f)
        )


        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TituloText(
                titulo = "BIENVENIDOS"
            )

            Spacer(modifier = Modifier.height(16.dp))

            //Subtítulo
            Text(
                text = "A LOS MIL SABORES",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFFC9ADA7),
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(60.dp))
            //campo pagina inicio
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.width(280.dp)
            ) {
                CampoTexto(
                    valor = usuario,
                    onValorCambio = {usuario = it
                        //Limpiar error cuando el usuario empiece a escribir
                        if (showError) showError = false},
                    etiqueta = "Usuario",
                    esError = showError && usuario.isBlank(),
                    mensajeError = if (showError && usuario.isBlank()) "Usuario requerido" else ""
                )

                Spacer(modifier = Modifier.height(16.dp))

                CampoTexto(
                    valor = contrasena,
                    onValorCambio = { contrasena = it
                        //Limpiar error cuando el usuario empiece a escribir
                        if (showError) showError = false
                    },
                    etiqueta = "Contraseña",
                    visualTransformation = PasswordVisualTransformation(),
                    esError = showError && contrasena.isBlank(),
                    mensajeError = if (showError && contrasena.isBlank()) "Contraseña requerida" else ""
                )

                if(mensaje.isNotEmpty()){
                    Text(
                        text = mensaje,
                        color = if(mensaje == "Login exitoso") Color(0xFF6D4C41) else Color(0xFFB56576),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                //boton
                BotonPastel(
                    "Ingresar",
                    onClickAccion = {
                        if (usuario.isBlank() || contrasena.isBlank()) {
                            showError = true //ESTO ACTIVA LAS ANIMACIONES
                            mensaje = "Complete todos los campos"
                            return@BotonPastel
                        }

                        showError = false // Desactiva-errores

                        CoroutineScope(Dispatchers.IO).launch {
                            val loginExitoso = viewModel.login(usuario, contrasena)

                            withContext(Dispatchers.Main) {
                                if (loginExitoso) {
                                    mensaje = "Login exitoso"
                                    navController.navigate("home/${usuario}") {  // Pasa el usuario como parámetro
                                        popUpTo("login") { inclusive = true }
                                    }
                                } else {
                                    mensaje = "Usuario o contraseña incorrectos"
                                    showError = true //Mostrar error con animación
                                }
                            }
                        }
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                BotonPastel(
                    "Dulce Registro",
                    onClickAccion = {
                        navController.navigate("registro")

                    }
                )

            }
        }
    }
}


