package com.spellbookapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Initialize Retrofit client for the API calls
object RetrofitClient {
    // The base url for the D&D API
    private const val BASE_URL = "https://www.dnd5eapi.co/api/"

    // Lazy initialization of the API using Retrofit builder
    val apiService: DndApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DndApiService::class.java)
    }
}
