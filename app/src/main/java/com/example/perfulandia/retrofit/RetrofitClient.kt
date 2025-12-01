package com.example.perfulandia.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    // TU URL REAL DE SUPABASE
    private const val BASE_URL = "https://ssxspldiwyaednmnooyy.supabase.co/"
    // TU CLAVE REAL DE SUPABASE
    private const val SUPABASE_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InNzeHNwbGRpd3lhZWRubW5vb3l5Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjQyMjYxNzcsImV4cCI6MjA3OTgwMjE3N30.csWkJ9Bi0xvoh7IqLiKv1GxRyaoTn0oyQVqKDJOg68s"

    // INTERCEPTOR: Inyecta la clave en cada petición automáticamente
    private val authInterceptor = okhttp3.Interceptor { chain ->
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .addHeader("apikey", SUPABASE_KEY)
            .addHeader("Authorization", "Bearer $SUPABASE_KEY")
            .addHeader("Content-Type", "application/json")
            .build()
        chain.proceed(newRequest)
    }

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .addInterceptor(loggingInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}