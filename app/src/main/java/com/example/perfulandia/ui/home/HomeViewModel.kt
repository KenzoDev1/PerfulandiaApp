package com.example.perfulandia.ui.home

import androidx.lifecycle.ViewModel
import com.example.perfulandia.catalogo.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadProducts()
    }

    private fun loadProducts() {
        // Simulate data loading
        val productList = listOf(
            Product(1, "Tommy Hilfinger Impact", 35000.0, 20),
            Product(2, "Versace Eros Flame", 55000.0, 10),
            Product(3, "Sauvage Elixir", 110000.0, 30),
            Product(4, "Bleu de Chanel", 120000.0, 15),
            Product(5, "Acqua di Gio", 90000.0, 25),
            Product(6, "One Million", 85000.0, 40)
        )
        _uiState.value = HomeUiState.Success(productList)
    }
}

sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Success(val products: List<Product>) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}
