package com.example.perfulandia.catalogo

import com.google.gson.annotations.SerializedName

data class Product (

    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("price")
    val price: Double,

    @SerializedName("stock")
    val stock: Int,

    @SerializedName("image_url")
    val imageUrl: String = ""
)