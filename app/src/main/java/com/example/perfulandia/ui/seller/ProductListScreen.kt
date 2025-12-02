package com.example.perfulandia.ui.seller

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.perfulandia.catalogo.Product
import com.example.perfulandia.ui.theme.PerfulandiaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen() {
    // CORRECCIÓN: Datos de ejemplo actualizados al nuevo modelo Product
    val products = listOf(
        Product(
            id = 1L,
            name = "Versace Eros",
            description = "Aromático Fougère para Hombres",
            price = 55000.0,
            stock = 15,
            imageUrl = "",
            categoryId = 1L
        ),
        Product(
            id = 2L,
            name = "Sauvage Dior",
            description = "Fresco, crudo y noble",
            price = 110000.0,
            stock = 8,
            imageUrl = "",
            categoryId = 1L
        ),
        Product(
            id = 3L,
            name = "Invictus",
            description = "El aroma de la victoria",
            price = 45000.0,
            stock = 25,
            imageUrl = "",
            categoryId = 1L
        ),
        Product(
            id = 4L,
            name = "One Million",
            description = "Cuero especiado",
            price = 62000.0,
            stock = 12,
            imageUrl = "",
            categoryId = 1L
        )
    )

    var searchQuery by remember { mutableStateOf("") }
    var categoryExpanded by remember { mutableStateOf(false) }
    val categories = listOf("Todas", "Hombre", "Mujer", "Unisex")
    var selectedCategory by remember { mutableStateOf(categories[0]) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Perfulandia - Vendedor") },
                navigationIcon = {
                    IconButton(onClick = { /* TODO: Open drawer */ }) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Default.Person, contentDescription = "Profile")
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    TextButton(onClick = { /*TODO*/ }) { Text("Inicio") }
                    TextButton(onClick = { /*TODO*/ }) { Text("Productos") }
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            item {
                Text("Productos registrados", style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(16.dp))

                // Filtros
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    label = { Text("Buscar por nombre") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Box {
                    OutlinedTextField(
                        value = selectedCategory,
                        onValueChange = {},
                        label = { Text("Filtrar por categoría:") },
                        readOnly = true,
                        trailingIcon = {
                            Icon(
                                Icons.Default.ArrowDropDown,
                                "Dropdown",
                                Modifier.clickable { categoryExpanded = true })
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                    DropdownMenu(
                        expanded = categoryExpanded,
                        onDismissRequest = { categoryExpanded = false }
                    ) {
                        categories.forEach { category ->
                            DropdownMenuItem(
                                text = { Text(category) },
                                onClick = {
                                    selectedCategory = category
                                    categoryExpanded = false
                                }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }

            // Encabezados de la tabla
            item {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Nombre", Modifier.weight(1f), fontWeight = FontWeight.Bold)
                    Text("Precio", Modifier.weight(0.7f), fontWeight = FontWeight.Bold)
                    Text("Stock", Modifier.weight(0.5f), fontWeight = FontWeight.Bold)
                }
                Divider()
            }

            // Lista de productos
            items(products) { product ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(product.name, Modifier.weight(1f))
                    Text("$${product.price.toInt()}", Modifier.weight(0.7f))
                    Text(product.stock.toString(), Modifier.weight(0.5f))
                }
                Divider()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductListScreenPreview() {
    PerfulandiaTheme {
        ProductListScreen()
    }
}