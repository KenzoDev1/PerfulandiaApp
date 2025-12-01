package com.example.perfulandia.ui.cart

import androidx.lifecycle.ViewModel
import com.example.perfulandia.model.CartItem
import com.example.perfulandia.model.Product
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
        // Simulate loading cart
        val items = listOf(
            CartItem(10L, Product(1, "Versace Eros Flame", 55000.0, 10), 1),
            CartItem(11L, Product(3, "Sauvage Elixir", 110000.0, 30), 2)
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
