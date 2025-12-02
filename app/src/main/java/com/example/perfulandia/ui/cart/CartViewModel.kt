package com.example.perfulandia.ui.cart

import androidx.lifecycle.ViewModel
import com.example.perfulandia.catalogo.Product
import com.example.perfulandia.model.CartItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CartViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<CartUiState>(CartUiState.Loading)
    val uiState: StateFlow<CartUiState> = _uiState.asStateFlow()

    init {
        loadCart()
    }

    private fun loadCart() {
        // CORRECCIÓN: Usamos nombres de parámetros para no confundirnos
        // y agregamos los campos nuevos (description, categoryId) y tipos correctos (Long).
        val items = listOf(
            CartItem(
                id = 10L,
                product = Product(
                    id = 1L,
                    name = "Versace Eros Flame",
                    description = "Fragancia intensa para hombres",
                    price = 55000.0,
                    stock = 10,
                    imageUrl = "",
                    categoryId = 1L
                ),
                stock = 1 // Cantidad en el carrito
            ),
            CartItem(
                id = 11L,
                product = Product(
                    id = 3L,
                    name = "Sauvage Elixir",
                    description = "Elixir concentrado y noble",
                    price = 110000.0,
                    stock = 30,
                    imageUrl = "",
                    categoryId = 1L
                ),
                stock = 2 // Cantidad en el carrito
            )
        )
        _uiState.value = CartUiState.Success(items)
    }

    fun updateQuantity(itemId: Long, newQuantity: Int) {
        val currentState = _uiState.value
        if (currentState is CartUiState.Success) {
            val updatedItems = currentState.items.map {
                if (it.id == itemId) it.copy(stock = newQuantity) else it
            }
            _uiState.value = CartUiState.Success(updatedItems)
        }
    }

    fun removeItem(itemId: Long) {
        val currentState = _uiState.value
        if (currentState is CartUiState.Success) {
            val updatedItems = currentState.items.filter { it.id != itemId }
            _uiState.value = CartUiState.Success(updatedItems)
        }
    }
}

sealed class CartUiState {
    object Loading : CartUiState()
    data class Success(val items: List<CartItem>) : CartUiState()
    data class Error(val message: String) : CartUiState()
}