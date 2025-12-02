package com.example.perfulandia.catalogo

import com.google.gson.annotations.SerializedName

data class Product (
    @SerializedName("id")
    val id: Long, // Importante: Long para Supabase

    @SerializedName("name")
    val name: String,

    @SerializedName("description")
    val description: String? = "", // Puede venir nulo o vacío

    @SerializedName("price")
    val price: Double,

    @SerializedName("stock")
    val stock: Int,

    @SerializedName("image_url")
    val imageUrl: String? = null,

    @SerializedName("category_id")
    val categoryId: Long? = 0L,

    @SerializedName("seller_id")
    val sellerId: String? = null
)