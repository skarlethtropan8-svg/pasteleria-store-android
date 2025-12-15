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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.pasteleriastore.R
import com.example.pasteleriastore.ui.components.BotonPastel
import com.example.pasteleriastore.ui.components.CampoTexto
import com.example.pasteleriastore.ui.components.TituloText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun RegistroProductoScreen(navController: NavController){
    var nombreProducto by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var activo by remember { mutableStateOf("true") }
    var imagen by remember { mutableStateOf("") }
    var mensajeError by remember { mutableStateOf("") }

    val viewModel: ProductoViewModel = viewModel ()

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ){
        //imagen de fondo
        Image(
            painter = painterResource(id = R.drawable.logocarrito),
            contentDescription = "Fondo pastel",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.5f)
        )
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(24.dp)
        ){
            // Título
            TituloText("Registrar Dulce Producto")

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Productos Mil Sabores",
                color = Color(0xFFC9ADA7),
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            if (mensajeError.isNotEmpty()) {
                Text(
                    text = mensajeError,
                    color = Color(0xFFB56576),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            // Campos del formulario
            CampoTexto(
                valor = nombreProducto,
                onValorCambio = {nombreProducto = it},
                etiqueta = "Nombre Producto *"
            )

            Spacer(modifier = Modifier.height(16.dp))

            CampoTexto(
                valor = descripcion,
                onValorCambio = {descripcion = it},
                etiqueta = "Descripcion *"
            )

            Spacer(modifier = Modifier.height(16.dp))

            CampoTexto(
                valor = precio,
                onValorCambio = {precio = it},
                etiqueta = "Precio 1000.00 *"
            )

            Spacer(modifier = Modifier.height(16.dp))
            CampoTexto(
                valor = stock,
                onValorCambio = {stock = it},
                etiqueta = "Stock *"
            )

            Spacer(modifier = Modifier.height(16.dp))

            CampoTexto(
                valor = categoria,
                onValorCambio = {categoria = it},
                etiqueta = "Categoria *"
            )
            Spacer(modifier = Modifier.height(16.dp))

            CampoTexto(
                valor = activo,
                onValorCambio = {activo = it},
                etiqueta = "Activo true/false tg"
            )



            Spacer(modifier = Modifier.height(24.dp))

            BotonPastel (
                texto = "Guardar Producto",
                onClickAccion = {
                    if (nombreProducto.isBlank() || descripcion.isBlank() || precio.isBlank() ||
                        stock.isBlank() || categoria.isBlank()) {
                        mensajeError = "Complete todos los campos (*) obligatorios"
                        return@BotonPastel
                    }
                    val precioDouble = try {
                        precio.toDouble()
                    } catch (e: NumberFormatException) {
                        mensajeError = "El precio debe ser un numero valido (ej: 29990.0)"
                        return@BotonPastel
                    }

                    if (precioDouble <= 0) {
                        mensajeError = "El precio debe ser mayor a 0"
                        return@BotonPastel
                    }

                    val stockInt = try {
                        stock.toInt()
                    } catch (e: NumberFormatException) {
                        mensajeError = "El stock debe ser un número entero valido"
                        return@BotonPastel
                    }

                    if (stockInt < 0) {
                        mensajeError = "El stock no puede ser negativo"
                        return@BotonPastel
                    }
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            val nuevoProducto = Producto(
                                nombreProducto = nombreProducto,
                                descripcion = descripcion,
                                precio = precio.toDouble(),
                                stock = stock.toInt(),
                                categoria = categoria,
                                activo = activo.toBoolean(),
                                imagen = if (imagen.isBlank()) "logocircular" else imagen
                            )

                            val cambiosExitosos = viewModel.guardarProducto(nuevoProducto)

                            withContext(Dispatchers.Main) {
                                if (cambiosExitosos) {
                                    mensajeError = "Producto guardado exitosamente"
                                    // Limpiar campos
                                    nombreProducto = ""
                                    descripcion = ""
                                    precio = ""
                                    stock = ""
                                    categoria = ""
                                    activo = "true"
                                    imagen = ""
                                } else {
                                    mensajeError = "Error al guardar producto"
                                }

                                if (cambiosExitosos) {
                                    kotlinx.coroutines.delay(1000)
                                    navController.popBackStack()
                                }
                            }
                        } catch (e: Exception) {
                            withContext(Dispatchers.Main) {
                                mensajeError = "Error: ${e.message}"
                            }
                        }

                    }
                }
            )


            Spacer(modifier = Modifier.height(16.dp))

            BotonPastel(
                texto = "Volver",
                onClickAccion = {
                    navController.popBackStack() //popBackStack cierra la pantalla actual y vuelve a la anterior
                    //navController.navigate("home")
                }
            )
        }
    }
}