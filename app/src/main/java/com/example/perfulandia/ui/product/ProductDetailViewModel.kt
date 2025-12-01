package com.example.perfulandia.ui.product

import androidx.lifecycle.ViewModel
import com.example.perfulandia.catalogo.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProductDetailViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<ProductDetailUiState>(ProductDetailUiState.Loading)
    val uiState: StateFlow<ProductDetailUiState> = _uiState.asStateFlow()

    fun loadProduct(productId: Int) {
        // Simulate fetching product
        // In a real app, this would come from a repository
        val product = Product(
            id = productId,
            name = "Versace Eros Flame", // Placeholder logic
            price = 55000.0,
            stock = 10
        )
        _uiState.value = ProductDetailUiState.Success(product)
    }

    fun addToCart(product: Product, quantity: Int) {
        // TODO: Implement Cart logic
    }
}

sealed class ProductDetailUiState {
    object Loading : ProductDetailUiState()
    data class Success(val product: Product) : ProductDetailUiState()
    data class Error(val message: String) : ProductDetailUiState()
}
