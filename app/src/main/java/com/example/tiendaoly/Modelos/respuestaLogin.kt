package com.example.tiendaoly.Modelos

import com.google.gson.annotations.SerializedName

data class respuestaLogin(
    val success: Boolean,
    val message: String,
    val alias: String,
    val rol: String,

    @SerializedName("usuario_id") val id: String
)