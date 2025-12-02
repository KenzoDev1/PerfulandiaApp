package com.example.perfulandia.ui.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perfulandia.catalogo.Product
import com.example.perfulandia.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class SearchUiState {
    object Idle : SearchUiState() // Estado inicial (sin búsqueda)
    object Loading : SearchUiState()
    data class Success(val products: List<Product>) : SearchUiState()
    data class Error(val message: String) : SearchUiState()
}

class SearchViewModel : ViewModel() {
    private val repository = ProductRepository()

    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Idle)
    val uiState: StateFlow<SearchUiState> = _uiState

    // Función de búsqueda real
    fun searchProducts(query: String) {
        if (query.isBlank()) {
            _uiState.value = SearchUiState.Idle
            return
        }

        viewModelScope.launch {
            _uiState.value = SearchUiState.Loading

            // 1. Obtenemos TODOS los productos de Supabase
            val result = repository.getProducts()

            result.onSuccess { allProducts ->
                // 2. Filtramos la lista en memoria (por nombre)
                val filteredList = allProducts.filter { product ->
                    product.name.contains(query, ignoreCase = true)
                }

                if (filteredList.isEmpty()) {
                    _uiState.value = SearchUiState.Error("No se encontraron productos")
                } else {
                    _uiState.value = SearchUiState.Success(filteredList)
                }
            }

            result.onFailure {
                _uiState.value = SearchUiState.Error("Error de conexión al buscar")
            }
        }
    }
}