package com.example.pasteleriastore.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pasteleriastore.ui.screen.CarritoScreen
import com.example.pasteleriastore.ui.screen.EditarDulcePerfilScreen
import com.example.pasteleriastore.ui.screen.HomeScreen
import com.example.pasteleriastore.ui.screen.LoginScreen
import com.example.pasteleriastore.ui.screen.PerfilScreen
import com.example.pasteleriastore.ui.screen.PostScreen
import com.example.pasteleriastore.ui.screen.ProductosScreen
import com.example.pasteleriastore.ui.screen.RegistroProductoScreen
import com.example.pasteleriastore.ui.screen.RegistroScreen
import com.example.pasteleriastore.ui.screen.SplashScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

     NavHost(navController = navController, startDestination = "splash") {
        composable("splash") { SplashScreen(navController = navController) }
        composable("login") { LoginScreen(navController = navController) }

        composable("home/{userNombre}") { backStackEntry ->
            val userNombre = backStackEntry.arguments?.getString("userNombre") ?: "Usuario"
            HomeScreen(
                navController = navController,
                userNombre = userNombre
            )
        }


         composable("registroProducto") { RegistroProductoScreen(navController = navController) }
         composable("registro") { RegistroScreen(navController = navController) }
         composable("productos") { ProductosScreen(navController = navController) }

        // MODIFICAR: Cambiar a userNombre temporalmente
        composable("editarPerfil/{userNombre}") { backStackEntry ->
            val userNombre = backStackEntry.arguments?.getString("userNombre") ?: ""
            EditarDulcePerfilScreen(
                navController = navController,
                userNombre = userNombre
            )


        }

        composable("perfil/{userNombre}") { backStackEntry ->
            val userNombre = backStackEntry.arguments?.getString("userNombre") ?: ""
            PerfilScreen(navController = navController, userNombre = userNombre)
        }

        composable("carrito/{userNombre}") { backStackEntry ->
            val userNombre = backStackEntry.arguments?.getString("userNombre") ?: "Usuario"
            CarritoScreen(
                navController = navController,
                userNombre = userNombre
            )
        }
         composable("posts") { PostScreen() }
    }
}


