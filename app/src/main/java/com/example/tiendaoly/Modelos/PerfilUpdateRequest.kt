package com.example.tiendaoly.Modelos

data class PerfilUpdateRequest(
    val id: String,
    val alias: String,
    val nombre: String,
    val apellido: String,
    val email: String,
    val telefono: String,
    val direccion: String,
    val fecha_nacimiento: String
)

data class GenericResponse(
    val success: Boolean,
    val message: String
)