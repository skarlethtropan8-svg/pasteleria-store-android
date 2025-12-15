package com.example.pasteleriastore.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.pasteleriastore.ui.components.BotonPastel
import com.example.pasteleriastore.R
import com.example.pasteleriastore.ui.components.TituloText
import com.example.pasteleriastore.viewmodel.CarritoViewModel




@Composable
fun CarritoScreen(navController: NavController, userNombre: String) {
    val carritoViewModel: CarritoViewModel = viewModel()
    val carrito by carritoViewModel.Carrito.collectAsState()

    LaunchedEffect(userNombre) {
        carritoViewModel.cargarCarrito(userNombre)
    }

    val total = carritoViewModel.calcularTotal()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Fondo de pantalla
        Image(
            painter = painterResource(id = R.drawable.logopasteleria),
            contentDescription = "Fondo Pastel carrito",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.6f)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // TituloText
            TituloText("Mi Dulce Carrito")

            Spacer(modifier = Modifier.height(16.dp))

            if (carrito.isEmpty()) {
                //Carrito vacío
                Text(
                    text = "Dulce Carrito vacío",
                    fontSize = 18.sp,
                    color = Color(0xFFBD9E97),
                    modifier = Modifier.padding(vertical = 32.dp)
                )
            } else {
                //Card para los detalles del carrito
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        //Lista de productos en el carrito
                        LazyColumn {
                           items(carrito)  { item ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {

                                Text(
                                        text = "${item.producto.nombreProducto} x ${item.cantidad}",
                                        color = Color.Black,
                                        fontSize = 16.sp,
                                        fontFamily = FontFamily.SansSerif
                                    )
                                    Text(
                                        text = "$${item.getSubtotal()}",
                                        color = Color(0xFF6D4C41),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp
                                    )
                                }
                                //Divider entre productos
                                Divider(
                                    color = Color(0xFFDEC198).copy(alpha = 0.3f),
                                    thickness = 1.dp,
                                    modifier = Modifier.padding(vertical = 4.dp)
                                )
                            }
                        }

                        // Total en la parte inferior de la Card
                        Spacer(modifier = Modifier.height(50.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "TOTAL:",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF6D4C41)
                            )
                            Text(
                                text = "$${String.format("%.2f", total)}",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFE8C07D
                                )
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BotonPastel(
                    texto = "Volver",
                    onClickAccion = {
                        navController.popBackStack()//te hace navegar a la pgina anterior
                        //navController.navigate("home")
                    }
                )
            }
        }
    }
}


