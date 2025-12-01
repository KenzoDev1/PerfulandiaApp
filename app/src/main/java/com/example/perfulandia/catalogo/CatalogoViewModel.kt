package com.example.perfulandia.catalogo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perfulandia.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class UiState {
    object Loading : UiState()
    data class Success(val products: List<Product>) : UiState()
    data class Error(val message: String) : UiState()
}

class CatalogViewModel : ViewModel() {

    private val repository = ProductRepository()

    // Estado privado (solo el ViewModel puede modificarlo)
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)

    // Estado público (la UI solo puede leerlo)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        // Se ejecuta cuando se crea el ViewModel
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {  // ← Coroutine que muere con el ViewModel
            _uiState.value = UiState.Loading

            repository.getProducts()
                .onSuccess { products ->
                    _uiState.value = UiState.Success(products)
                }
                .onFailure { exception ->
                    _uiState.value = UiState.Error(
                        exception.message ?: "Error desconocido"
                    )
                }
        }
    }

    fun retry() {
        loadProducts()
    }
}