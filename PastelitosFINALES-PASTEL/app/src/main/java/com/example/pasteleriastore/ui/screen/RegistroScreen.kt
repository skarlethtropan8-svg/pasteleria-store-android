package com.example.pasteleriastore.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.pasteleriastore.model.Usuario
import androidx.compose.ui.res.painterResource
import com.example.pasteleriastore.R
import com.example.pasteleriastore.ui.components.*
import com.example.pasteleriastore.viewmodel.LoginViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

//@Preview
@Composable
fun RegistroScreen(navController: NavController) {
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var confirmarContrasena by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var mensajeError by remember { mutableStateOf("") }

    val viewModel: LoginViewModel = viewModel()

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        //  Fondo pastel suave
        Image(
            painter = painterResource(id = R.drawable.logop),
            contentDescription = "Fondo registro",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.25f) //  Más suave para estética pastel
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp)
        ) {

            //  Título pastel
            TituloText(titulo = "Crear Dulce Cuenta")

            Spacer(modifier = Modifier.height(4.dp))

            //  Subtítulo pastel
            Text(
                text = "Únete a los Mil Sabores",
                color = Color(0xFFC9ADA7), // rosa viejo pastel
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            //  Mensaje de error pastel
            if (mensajeError.isNotEmpty()) {
                Text(
                    text = mensajeError,
                    color = Color(0xFFB56576), // rojo pastel suave
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            //  Campos pastel
            CampoTexto(
                valor = nombre,
                onValorCambio = { nombre = it },
                etiqueta = "Nombre de usuario"
            )

            Spacer(modifier = Modifier.height(16.dp))

            CampoTexto(
                valor = correo,
                onValorCambio = { correo = it },
                etiqueta = "Correo electrónico"
            )

            Spacer(modifier = Modifier.height(16.dp))

            CampoTexto(
                valor = contrasena,
                onValorCambio = { contrasena = it },
                etiqueta = "Contraseña",
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(16.dp))

            CampoTexto(
                valor = confirmarContrasena,
                onValorCambio = { confirmarContrasena = it },
                etiqueta = "Confirmar contraseña",
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(16.dp))

            CampoTexto(
                valor = direccion,
                onValorCambio = { direccion = it },
                etiqueta = "Dirección"
            )

            Spacer(modifier = Modifier.height(16.dp))

            CampoTexto(
                valor = telefono,
                onValorCambio = { telefono = it },
                etiqueta = "Teléfono"
            )

            Spacer(modifier = Modifier.height(24.dp))

            //Botón pastel
            BotonPastel(
                texto = "Guardar con Dulzura",
                onClickAccion = {
                    if (nombre.isBlank() || contrasena.isBlank() || correo.isBlank())
                    {
                        mensajeError = "Por favor completa todos los campos (*) obligatorios"
                        return@BotonPastel
                    }

                    if (!correo.contains("@milsabores.cl")) {
                        mensajeError = "Ingresa un correo electrónico válido de Mil Sabores"
                        return@BotonPastel
                    }

                    if (contrasena != confirmarContrasena) {
                        mensajeError = "Las contraseñas no coinciden"
                        return@BotonPastel
                    }

                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            val nuevoUsuario = Usuario(
                                nombre = nombre,
                                contrasena = contrasena,
                                correo = correo,
                                telefono = telefono,
                                direccion = direccion
                            )

                            val registroExitoso = viewModel.registroUsuario(nuevoUsuario)

                            withContext(Dispatchers.Main) {
                                if (registroExitoso) {
                                    navController.navigate("login")
                                } else {
                                    mensajeError = "Este cliente ya está registrado"
                                }
                            }

                        } catch (e: Exception) {
                            withContext(Dispatchers.Main) {
                                mensajeError = "Ups... ocurrió un error al registrar: ${e.message}"
                            }
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            BotonPastel(
                texto = "Volver al inicio",
                onClickAccion = { navController.navigate("login") }
            )
        }
    }
}


