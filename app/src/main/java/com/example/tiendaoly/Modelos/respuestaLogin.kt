package com.example.tiendaoly.Modelos

data class respuestaLogin(
    val success: Boolean,
    val message: String,

    // Agregamos '?' para que acepten nulos si la API no los envía
    val usuario_id: Int ? = null,
    val alias: String ? = null,
    val rol: String ? = null
)