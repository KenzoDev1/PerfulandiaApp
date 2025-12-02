package com.example.perfulandia.ui.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perfulandia.catalogo.Product
import com.example.perfulandia.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class ProductDetailUiState {
    object Loading : ProductDetailUiState()
    data class Success(val product: Product) : ProductDetailUiState()
    data class Error(val message: String) : ProductDetailUiState()
}

class ProductDetailViewModel : ViewModel() {
    private val repository = ProductRepository()

    private val _uiState = MutableStateFlow<ProductDetailUiState>(ProductDetailUiState.Loading)
    val uiState: StateFlow<ProductDetailUiState> = _uiState

    fun loadProduct(productId: Long) {
        viewModelScope.launch {
            _uiState.value = ProductDetailUiState.Loading
            try {
                // Aquí deberías tener un método en tu repo para buscar por ID.
                // Si no lo tienes, por ahora filtramos de la lista completa (menos eficiente pero funciona)
                val result = repository.getProducts()

                result.onSuccess { products ->
                    val product = products.find { it.id == productId }
                    if (product != null) {
                        _uiState.value = ProductDetailUiState.Success(product)
                    } else {
                        _uiState.value = ProductDetailUiState.Error("Producto no encontrado")
                    }
                }
                result.onFailure {
                    _uiState.value = ProductDetailUiState.Error("Error al cargar")
                }
            } catch (e: Exception) {
                _uiState.value = ProductDetailUiState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    fun addToCart(product: Product, quantity: Int) {
        // Lógica de carrito pendiente
    }
}