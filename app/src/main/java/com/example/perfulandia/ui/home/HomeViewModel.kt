package com.example.perfulandia.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perfulandia.catalogo.Product
import com.example.perfulandia.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val repository = ProductRepository()

    // Estas son las variables que busca tu HomeScreen
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        fetchProducts()
    }

    fun fetchProducts() {
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.getProducts() // Asegúrate que tu repositorio devuelva Result<List<Product>>

            // Si tu repositorio devuelve la lista directa (sin Result), usa:
            // _products.value = result

            // Si usa Result:
            result.onSuccess { list -> _products.value = list }

            _isLoading.value = false
        }
    }
}