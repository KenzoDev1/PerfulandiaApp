package com.example.perfulandia.ui.product

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.perfulandia.ui.theme.Gold
import com.example.perfulandia.ui.theme.GoldDim
import com.example.perfulandia.ui.theme.PerfulandiaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    productId: Long,
    navController: NavController,
    viewModel: ProductDetailViewModel = viewModel()
) {
    LaunchedEffect(productId) {
        viewModel.loadProduct(productId)
    }

    val uiState by viewModel.uiState.collectAsState()
    var quantity by remember { mutableStateOf(1) }

    com.example.perfulandia.ui.components.BackgroundWrapper(
        backgroundImageId = com.example.perfulandia.R.drawable.background_elegant
    ) {
        Scaffold(
            containerColor = Color.Transparent,
            // Se tuvo que realizar un cambio en el TopAppBar
            topBar = {
                TopAppBar(
                    title = {},
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Gold)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent
                    )
                )
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                when (val state = uiState) {
                    is ProductDetailUiState.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center),
                            color = Gold
                        )
                    }
                    is ProductDetailUiState.Error -> {
                        Text(
                            text = state.message,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    is ProductDetailUiState.Success -> {
                        val product = state.product
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                                .padding(16.dp)
                        ) {
                            // Product Image Placeholder
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(350.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(Color.White) // Fondo blanco por si la imagen es PNG transparente
                                    .border(1.dp, GoldDim, RoundedCornerShape(16.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                if (product.imageUrl.isNullOrBlank()) {
                                    // Si NO hay link, mostramos la caja gris con la letra (tu diseño original)
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(Color.DarkGray),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = product.name.take(1).uppercase(),
                                            style = MaterialTheme.typography.displayLarge,
                                            color = Gold.copy(alpha = 0.5f)
                                        )
                                    }
                                } else {
                                    // Si HAY link, cargamos la foto real con Coil
                                    AsyncImage(
                                        model = product.imageUrl,
                                        contentDescription = product.name,
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Fit
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(24.dp))

                            // Title and Price
                            Text(
                                text = product.name,
                                style = MaterialTheme.typography.headlineMedium,
                                color = MaterialTheme.colorScheme.onBackground
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "$${product.price.toInt()}",
                                style = MaterialTheme.typography.headlineSmall,
                                color = Gold,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(24.dp))

                            // Description
                            Text(
                                text = "Description",
                                style = MaterialTheme.typography.titleMedium,
                                color = Gold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = if (product.description.isNullOrBlank()) "Sin descripción disponible." else product.description,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
                                textAlign = TextAlign.Justify
                            )

                            Spacer(modifier = Modifier.height(32.dp))

                            // Quantity Selector
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                IconButton(
                                    onClick = { if (quantity > 1) quantity-- },
                                    modifier = Modifier.border(1.dp, Gold, RoundedCornerShape(8.dp))
                                ) {
                                    Icon(Icons.Default.Remove, contentDescription = "Decrease", tint = Gold)
                                }

                                Text(
                                    text = quantity.toString(),
                                    style = MaterialTheme.typography.titleLarge,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    modifier = Modifier.padding(horizontal = 24.dp)
                                )

                                IconButton(
                                    onClick = { quantity++ },
                                    modifier = Modifier.border(1.dp, Gold, RoundedCornerShape(8.dp))
                                ) {
                                    Icon(Icons.Default.Add, contentDescription = "Increase", tint = Gold)
                                }
                            }

                            Spacer(modifier = Modifier.height(32.dp))

                            // Add to Cart Button
                            Button(
                                onClick = { viewModel.addToCart(product, quantity) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Gold,
                                    contentColor = Color.Black
                                ),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = "ADD TO CART",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
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
fun ProductDetailScreenPreview() {
    PerfulandiaTheme {
        val navController = rememberNavController()
        ProductDetailScreen(productId = 1, navController = navController)
    }
}