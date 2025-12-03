package com.example.perfulandia.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.perfulandia.catalogo.Product
import com.example.perfulandia.ui.navigation.AppRoutes
import com.example.perfulandia.ui.theme.Gold
import com.example.perfulandia.ui.theme.GoldDim
import coil.compose.AsyncImage
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = viewModel() // Inyectamos el ViewModel aquí
) {
    // Escuchamos los datos REALES de la base de datos
    val products by viewModel.products.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    com.example.perfulandia.ui.components.BackgroundWrapper(
        backgroundImageId = com.example.perfulandia.R.drawable.background_collection
    ) {
        Scaffold(
            containerColor = Color.Transparent
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                // Header / Title
                Text(
                    text = "Perfulandia",
                    style = MaterialTheme.typography.displaySmall,
                    color = Gold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "Perfumes y Fragancias",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                // Lógica de carga: Spinner o Lista
                if (isLoading) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = Gold)
                    }
                } else {
                    if (products.isEmpty()) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("No hay productos disponibles.", color = Color.Gray)
                        }
                    } else {
                        // Pasamos la lista real 'products' a la grilla
                        ProductGrid(products = products, navController = navController)
                    }
                }
            }
        }
    }
}

@Composable
fun ProductGrid(products: List<Product>, navController: NavController) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        items(products) { product ->
            ProductCard(product) {
                navController.navigate("${AppRoutes.PRODUCT_DETAIL_SCREEN}/${product.id}")
            }
        }
    }
}

@Composable
fun ProductCard(product: Product, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // IMAGEN DEL PRODUCTO (Actualizado)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f) // Mantiene el cuadro cuadrado
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color.White) // Fondo blanco para que resalte la imagen
                    .border(1.dp, GoldDim, RoundedCornerShape(4.dp)),
                contentAlignment = Alignment.Center
            ) {
                if (product.imageUrl.isNullOrBlank()) {
                    // Placeholder si no hay imagen (Caja Gris con Letra)
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.DarkGray),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = product.name.take(1).uppercase(),
                            style = MaterialTheme.typography.displayMedium,
                            color = Gold.copy(alpha = 0.5f)
                        )
                    }
                } else {
                    // Imagen Real (Ajustada con FIT)
                    AsyncImage(
                        model = product.imageUrl,
                        contentDescription = product.name,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Fit // <--- ESTO HACE QUE CALCE PERFECTO
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Nombre del Producto
            Text(
                text = product.name,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Precio
            Text(
                text = "$${product.price.toInt()}",
                style = MaterialTheme.typography.titleMedium,
                color = Gold,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Botón visual
            Button(
                onClick = { /* TODO: Add to cart */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Gold,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(4.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 0.dp),
                modifier = Modifier.height(32.dp)
            ) {
                Text("Add", style = MaterialTheme.typography.labelMedium)
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF121212)
@Composable
fun HomeScreenPreview() {
}