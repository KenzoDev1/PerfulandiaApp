package com.example.perfulandia.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.perfulandia.catalogo.Product
import com.example.perfulandia.ui.cart.ShoppingCartScreen
import com.example.perfulandia.ui.home.AboutScreen
import com.example.perfulandia.ui.home.ContactScreen
import com.example.perfulandia.ui.home.HomeScreen
import com.example.perfulandia.ui.product.ProductDetailScreen
import com.example.perfulandia.ui.product.SearchResultsScreen

object AppRoutes {
    const val HOME_SCREEN = "home"
    const val SEARCH_SCREEN = "search"
    const val CART_SCREEN = "cart"
    const val ABOUT_SCREEN = "about"
    const val CONTACT_SCREEN = "contact"
    const val PRODUCT_DETAIL_SCREEN = "product_detail"
    // Eliminamos LOGIN y REGISTER de aquí porque los maneja MainActivity
}

@Composable
fun AppNavigation(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        // CORRECCIÓN CLAVE: Al entrar aquí, ya estamos logueados, así que vamos directo al Home
        startDestination = AppRoutes.HOME_SCREEN,
        modifier = modifier
    ) {
        // --- HOME (Ahora es la pantalla principal) ---
        composable(route = AppRoutes.HOME_SCREEN) {
            HomeScreen(navController)
        }

        // --- OTRAS PANTALLAS DEL MENÚ ---
        composable(route = AppRoutes.SEARCH_SCREEN) {
            SearchResultsScreen(navController)
        }
        composable(route = AppRoutes.CART_SCREEN) {
            ShoppingCartScreen()
        }
        composable(route = AppRoutes.ABOUT_SCREEN) {
            AboutScreen()
        }
        composable(route = AppRoutes.CONTACT_SCREEN) {
            ContactScreen()
        }

        // --- DETALLE DE PRODUCTO ---
        composable(
            route = "${AppRoutes.PRODUCT_DETAIL_SCREEN}/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.LongType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getLong("productId") ?: 0L

            // Llamamos a la pantalla pasando SOLO el ID (que ya arreglamos antes)
            ProductDetailScreen(productId = productId, navController = navController)
        }
    }
}