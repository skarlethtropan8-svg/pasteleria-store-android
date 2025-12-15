package com.example.pasteleriastore.ui.screen

import android.service.autofill.OnClickAction
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.pasteleriastore.R
import com.example.pasteleriastore.model.Producto
import com.example.pasteleriastore.ui.components.BotonPastel
import com.example.pasteleriastore.viewmodel.CarritoViewModel
import com.example.pasteleriastore.viewmodel.ProductoViewModel



@Composable
fun ProductosScreen(navController: NavController) {
    val viewModel: ProductoViewModel = viewModel()
    val productos by viewModel.productos.collectAsState()
    val carritoViewModel: CarritoViewModel = viewModel()
    val categorias by viewModel.categorias.collectAsState()

    val usuarioActual = "cliente"

    LaunchedEffect(Unit) {
        viewModel.cargarProductos()
        carritoViewModel.cargarCarrito(usuarioActual)
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {

        // Header pastel
        Column {
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Bienvenidos al Rincón Dulce",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF6D4C41) // Marrón pastel elegante
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Productos Deli disponibles",
                fontSize = 18.sp,
                color = Color(0xFFC9ADA7) // Rosa viejo pastel
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Categorías pastel
        if (categorias.isNotEmpty()) {

            Text(
                text = "Filtrar por categoría:",
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = Color(0xFFBFA6A0), // Gris pastel suave
                modifier = Modifier.padding(bottom = 8.dp)
            )

            LazyRow(
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                items(categorias) { categoria ->

                    Text(
                        text = categoria,
                        color = Color.White,
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .background(
                                color = Color(0xFFE8C07D), // Dorado pastel
                                shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp)
                            )
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .clickable {
                                if (categoria == "Todos") {
                                    viewModel.cargarProductos()
                                } else {
                                    viewModel.cargarProductosPorCategoria(categoria)
                                }
                            }
                    )
                }
            }
        }

        // Lista de productos pastel
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(productos) { productos ->
                ProductoItem(
                    producto = productos,
                    carritoViewModel = carritoViewModel,
                    usuarioActual = usuarioActual
                )
            }
        }

        // Botón inferior pastel
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            BotonPastel("Volver") {
                navController.popBackStack()
            }
        }
    }
}



@Composable
fun ProductoItem(producto: Producto, carritoViewModel: CarritoViewModel, usuarioActual: String) {
    val context = LocalContext.current
    val imageResource = obtieneImagen(context, producto.imagen)

    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {

        Image(
            painter = painterResource(id = imageResource),
            contentDescription = "Imagen de ${producto.nombreProducto}",
            modifier = Modifier
                .size(100.dp)
                .padding(end = 16.dp)
        )

        Column {

            Text(
                text = producto.nombreProducto,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF6D4C41) // Marrón pastel
            )

            Text(
                text = "$${producto.precio}",
                color = Color(0xFFC9ADA7) // Rosa pastel
            )

            Text(
                text = producto.descripcion,
                fontSize = 12.sp,
                color = Color(0xFFBFA6A0) // Gris pastel suave
            )

            Spacer(modifier = Modifier.height(8.dp))

            BotonPastel(
                texto = "Agregar al Carrito",
                onClickAccion = {
                    carritoViewModel.agregarProducto(usuarioActual, producto)
                }
            )
        }
    }
}



private fun obtieneImagen(context: android.content.Context, imagen: String?): Int {
    val nombre = imagen?.replace(".png", "")?.replace(".jpg", "") ?: "logocircular"
    val resourceId = context.resources.getIdentifier(nombre, "drawable", context.packageName)
    return if (resourceId == 0) R.drawable.logoc else resourceId
}