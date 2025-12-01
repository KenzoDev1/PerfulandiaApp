package com.example.perfulandia.retrofit

import com.example.perfulandia.catalogo.Product
import com.example.perfulandia.model.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiService {

    @Headers(
        "apikey: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImF2aWZrendyaGVucHpuaHRqdGxuIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjQxNzExODMsImV4cCI6MjA3OTc0NzE4M30.V_wYJcosi3gdiMNs7F7CQUswZsYfW1sjzC0FKJY37Gc",
        "Authorization: "
    )

    @GET("products")
    suspend fun getProducts(): Response<List<Product>>

    @GET("clients")
    suspend fun getClients(): Response<List<User>>
}