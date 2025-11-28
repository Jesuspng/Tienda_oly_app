package com.example.tiendaoly.Modelos

data class respuestaLogin(
    val success: Boolean,
    val message: String,
    val usuario_id: Int ? = null,
    val alias: String ? = null,
    val rol: String ? = null,
    val id: String
)