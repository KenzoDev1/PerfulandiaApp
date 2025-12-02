package com.example.perfulandia.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.perfulandia.ui.navigation.AppNavigation
import com.example.perfulandia.ui.navigation.AppRoutes
import com.example.perfulandia.ui.theme.PerfulandiaTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(
    onSignOut: () -> Unit // Recibimos la acción de cerrar sesión
) {
    val navController = rememberNavController()

    // FORZAMOS que inicie CERRADO (Closed)
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val scope = rememberCoroutineScope()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    "Perfulandia",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.titleMedium
                )

                HorizontalDivider() // Una línea bonita

                // Ítems de Navegación
                NavigationDrawerItem(
                    label = { Text("Inicio") },
                    selected = currentRoute == AppRoutes.HOME_SCREEN,
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate(AppRoutes.HOME_SCREEN)
                    }
                )
                NavigationDrawerItem(
                    label = { Text("Nosotros") },
                    selected = currentRoute == AppRoutes.ABOUT_SCREEN,
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate(AppRoutes.ABOUT_SCREEN)
                    }
                )
                NavigationDrawerItem(
                    label = { Text("Contacto") },
                    selected = currentRoute == AppRoutes.CONTACT_SCREEN,
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate(AppRoutes.CONTACT_SCREEN)
                    }
                )

                Spacer(modifier = Modifier.weight(1f)) // Empuja lo siguiente al fondo
                HorizontalDivider()

                // Botón de CERRAR SESIÓN
                NavigationDrawerItem(
                    label = { Text("Cerrar sesión") },
                    icon = { Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = null) },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        onSignOut() // Llamamos a la acción de salir
                    }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                PerfulandiaTopBar(
                    onMenuClick = { scope.launch { drawerState.open() } },
                    onSearchClick = { navController.navigate(AppRoutes.SEARCH_SCREEN) },
                    onCartClick = { navController.navigate(AppRoutes.CART_SCREEN) }
                )
            }
        ) { paddingValues ->
            AppNavigation(
                navController,
                Modifier.padding(paddingValues)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfulandiaTopBar(
    onMenuClick: () -> Unit,
    onSearchClick: () -> Unit,
    onCartClick: () -> Unit
) {
    TopAppBar(
        title = { Text("Perfulandia") },
        navigationIcon = {
            IconButton(onClick = onMenuClick) {
                Icon(Icons.Default.Menu, contentDescription = "Menú")
            }
        },
        actions = {
            IconButton(onClick = onSearchClick) {
                Icon(Icons.Default.Search, contentDescription = "Buscar")
            }
            IconButton(onClick = onCartClick) {
                Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito")
            }
        }
    )
}

@Preview
@Composable
fun MainScaffoldPreview() {
    PerfulandiaTheme {
        MainScaffold(onSignOut = {})
    }
}