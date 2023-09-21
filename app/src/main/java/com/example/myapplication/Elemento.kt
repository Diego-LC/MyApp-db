package com.example.myapplication

import retrofit2.Call
import retrofit2.http.GET

data class Elemento(
    val nombre: String,
    val apellido: String,
    val rut: Int)

interface ApiService {

    @GET("todos") // Coloca la URL correcta aquí
    fun obtenerLista(): Call<List<Elemento>>
}

