package com.example.perfulandia.model

import com.example.perfulandia.catalogo.Product

data class CartItem (
    val id: Long,
    val product: Product,
    val stock: Int
)