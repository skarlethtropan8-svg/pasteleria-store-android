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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.pasteleriastore.R
import com.example.pasteleriastore.ui.components.BotonPastel
import com.example.pasteleriastore.ui.components.CampoTexto
import com.example.pasteleriastore.ui.components.TituloText
import kotlinx.coroutines.launch
import com.example.pasteleriastore.viewmodel.PerfilViewModel




@Composable
fun EditarDulcePerfilScreen (navController: NavController, userNombre: String){
    val viewModel: PerfilViewModel = viewModel()
    val coroutineScope = rememberCoroutineScope()

    var nombre by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var mensajeError by remember { mutableStateOf("") }
    var userId by remember { mutableStateOf<Int?>(null) }

    LaunchedEffect(userNombre) {
        if (userNombre.isNotBlank()) {
            val usuarioActual = viewModel.cargarUsuario(userNombre)
            usuarioActual?.let {
                userId = it.id // ← Obtener el ID del usuario
                nombre = it.nombre
                contrasena = it.contrasena
                correo = it.correo
                telefono = it.telefono ?: ""
                direccion = it.direccion ?: ""
            } ?: run {
                mensajeError = "Error: Usuario no encontrado"
            }
        } else {
            mensajeError = "Error: No se proporcionó nombre de usuario"
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.logop),
            contentDescription = "Fondo pasteleria login",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.7f)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(24.dp)
        ) {
            TituloText("Editar Dulce Perfil")

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Modifica solo los campos que deseas cambiar",
                color = Color(0xFFEFD0B3),
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(16.dp))

            if (mensajeError.isNotEmpty()) {
                Text(
                    text = mensajeError,
                    color = if (mensajeError.contains("correctamente")) Color(0xFF98EE9B) else Color(
                        0xFFFA8F9D
                    ),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            // Campos del formulario
            CampoTexto(
                valor = nombre,
                onValorCambio = { nombre = it },
                etiqueta = "Nombre Usuario"
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
                valor = correo,
                onValorCambio = { correo = it },
                etiqueta = "Correo"
            )

            Spacer(modifier = Modifier.height(16.dp))

            CampoTexto(
                valor = telefono,
                onValorCambio = { telefono = it },
                etiqueta = "Teléfono (opcional)"
            )

            Spacer(modifier = Modifier.height(16.dp))

            CampoTexto(
                valor = direccion,
                onValorCambio = { direccion = it },
                etiqueta = "Dirección (opcional)"
            )

            Spacer(modifier = Modifier.height(24.dp))

            BotonPastel(
                texto = "Guardar Cambios",
                onClickAccion = {
                    // Validación del correo si se modifica
                    if (correo.isNotBlank() && !correo.contains("@milsabores.cl")) {
                        mensajeError = "Ingresa un correo electrónico válido (@milsabores.cl)"
                        return@BotonPastel
                    }

                    if (userId == null) {
                        mensajeError = "Error: No se pudo identificar el usuario"
                        return@BotonPastel
                    }

                    coroutineScope.launch {
                        val cambiosexitos = viewModel.actualizarUsuarioPorId(
                            usuarioId = userId!!,
                            nuevoNombre = if (nombre.isNotBlank()) nombre else null,
                            nuevaContrasena = if (contrasena.isNotBlank()) contrasena else null,
                            nuevoEmail = if (correo.isNotBlank()) correo else null,
                            nuevoTelefono = if (telefono.isNotBlank()) telefono else null,
                            nuevaDireccion = if (direccion.isNotBlank()) direccion else null
                        )

                        if (cambiosexitos) {
                            mensajeError = "Perfil actualizado correctamente"

                            // NUEVO: Navegar de vuelta a home con el NUEVO nombre
                            kotlinx.coroutines.delay(1000)

                            // Si el nombre cambió, usar el nuevo nombre, sino mantener el actual
                            val nombreParaNavegar = if (nombre.isNotBlank() && nombre != userNombre) {
                                nombre
                            } else {
                                userNombre
                            }

                            // Navegar a home con el nombre actualizado
                            navController.navigate("home/${nombreParaNavegar}") {
                                // Limpiar el back stack para que no pueda volver a editarPerfil
                                popUpTo("home/${userNombre}") { inclusive = true }
                            }
                        } else {
                            mensajeError = "Error al actualizar perfil"
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            BotonPastel(
                texto = "Cancelar",
                onClickAccion = {
                    navController.popBackStack()
                }
            )
        }
    }
}





