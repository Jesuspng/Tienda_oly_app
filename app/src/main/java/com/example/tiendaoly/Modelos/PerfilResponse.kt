package com.example.tiendaoly.Modelos

import com.google.gson.annotations.SerializedName

data class PerfilResponse(
    val success: Boolean,
    val message: String?,
    val perfil: PerfilData?
)

data class PerfilData(
    val alias: String,
    val nombre: String,
    val apellido: String,
    val email: String,
    val telefono: String?,
    val direccion: String?,
    @SerializedName("fecha_nacimiento") val fechaNacimiento: String?
)