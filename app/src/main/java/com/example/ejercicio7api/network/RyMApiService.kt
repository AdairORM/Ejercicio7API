package com.example.ejercicio7api.network

import com.example.ejercicio7api.model.Character
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET

private const val BASE_URL = "https://rickandmortyapi.com/"

private val json = Json { ignoreUnknownKeys = true }

private val retrofit = Retrofit.Builder()
    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

interface RyMApiService{
    @GET("api/character/[1,2,3,4,5,33,89,55,100,101,99]") // Obtener 10 personajes
    suspend fun getCharacters(): List<Character>
}

object RyMApi{
    val retrofitService: RyMApiService by lazy{
        retrofit.create(RyMApiService::class.java)
    }
}
