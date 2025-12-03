package com.example.perfulandia.ui.product

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.perfulandia.ui.home.ProductCard
import com.example.perfulandia.ui.navigation.AppRoutes
import com.example.perfulandia.ui.theme.Gold
import com.example.perfulandia.ui.theme.PerfulandiaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchResultsScreen(
    navController: NavController,
    viewModel: SearchViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    // CORRECCIÓN 1: Manejamos el texto aquí localmente para no complicar el ViewModel
    var query by remember { mutableStateOf("") }

    com.example.perfulandia.ui.components.BackgroundWrapper(
        backgroundImageId = com.example.perfulandia.R.drawable.background_collection
    ) {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                // Search Bar (Tu diseño intacto)
                TextField(
                    value = query,
                    onValueChange = {
                        query = it
                        viewModel.searchProducts(it) // Llamamos a la búsqueda real
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    placeholder = { Text("Buscar perfumes...", color = Color.Gray) },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = Gold) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        disabledContainerColor = MaterialTheme.colorScheme.surface,
                        focusedIndicatorColor = Gold,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                    ),
                    shape = RoundedCornerShape(8.dp),
                    singleLine = true
                )
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp)
            ) {
                // CORRECCIÓN 2: Adaptamos el 'when' a los estados del nuevo ViewModel
                when (val state = uiState) {
                    is SearchUiState.Idle -> { // Antes era Initial
                        Text(
                            text = "Empezando a buscar...",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Gray,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    is SearchUiState.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center),
                            color = Gold
                        )
                    }
                    is SearchUiState.Error -> {
                        Text(
                            text = state.message,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    is SearchUiState.Success -> {
                        // Verificamos aquí si la lista está vacía
                        if (state.products.isEmpty()) {
                            Text(
                                text = "No products found.",
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.Gray,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        } else {
                            // Si hay productos, mostramos tu diseño de resultados
                            Column {
                                Text(
                                    text = "Results",
                                    style = MaterialTheme.typography.titleLarge,
                                    color = Gold,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )

                                LazyVerticalGrid(
                                    columns = GridCells.Fixed(2),
                                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(16.dp),
                                    contentPadding = PaddingValues(bottom = 16.dp)
                                ) {
                                    items(state.products) { product ->
                                        ProductCard(product = product) {
                                            navController.navigate("${AppRoutes.PRODUCT_DETAIL_SCREEN}/${product.id}")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF121212)
@Composable
fun SearchResultsScreenPreview() {
    PerfulandiaTheme {
        val navController = rememberNavController()
        SearchResultsScreen(navController = navController)
    }
}