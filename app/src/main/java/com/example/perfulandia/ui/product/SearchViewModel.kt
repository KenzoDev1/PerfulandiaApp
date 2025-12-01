package com.example.perfulandia.ui.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perfulandia.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Initial)
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    private val allProducts = listOf(
        Product(1, "Versace Eros Flame", 55000.0, 10),
        Product(2, "Tommy Hilfinger Impact", 35000.0, 20),
        Product(3, "Sauvage Elixir", 110000.0, 30),
        Product(4, "Bleu de Chanel", 120000.0, 15),
        Product(5, "Acqua di Gio", 90000.0, 25),
        Product(6, "One Million", 85000.0, 40)
    )

    fun onQueryChange(newQuery: String) {
        _query.value = newQuery
        if (newQuery.isBlank()) {
            _uiState.value = SearchUiState.Initial
        } else {
            searchProducts(newQuery)
        }
    }

    private fun searchProducts(query: String) {
        viewModelScope.launch {
            _uiState.value = SearchUiState.Loading
            // Simulate network delay
            // delay(500) 
            val filtered = allProducts.filter {
                it.name.contains(query, ignoreCase = true)
            }
            if (filtered.isEmpty()) {
                _uiState.value = SearchUiState.Empty
            } else {
                _uiState.value = SearchUiState.Success(filtered)
            }
        }
    }
}

sealed class SearchUiState {
    object Initial : SearchUiState()
    object Loading : SearchUiState()
    object Empty : SearchUiState()
    data class Success(val products: List<Product>) : SearchUiState()
    data class Error(val message: String) : SearchUiState()
}
