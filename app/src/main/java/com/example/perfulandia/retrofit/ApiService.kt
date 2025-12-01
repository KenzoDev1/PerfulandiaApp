package com.example.perfulandia.retrofit

import com.example.perfulandia.catalogo.Product
import com.example.perfulandia.model.AuthResponse
import com.example.perfulandia.model.LoginRequest
import com.example.perfulandia.model.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    // --- PRODUCTOS ---
    @GET("rest/v1/products")
    suspend fun getProducts(): Response<List<Product>>

    // --- AUTENTICACIÓN ---
    @POST("auth/v1/signup")
    suspend fun signUp(@Body request: RegisterRequest): Response<AuthResponse>

    @POST("auth/v1/token?grant_type=password")
    suspend fun signIn(@Body request: LoginRequest): Response<AuthResponse>
}