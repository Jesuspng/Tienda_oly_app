package com.example.tiendaoly.Modelos

import com.google.gson.annotations.SerializedName
data class UsuarioResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("usuarios") val usuarios: List<UsuarioItem>?
)

data class UsuarioItem(
    @SerializedName("id") val id: String,
    @SerializedName("alias") val alias: String,
    @SerializedName("rol") val rol: String,
    @SerializedName("activo") val activo: Boolean
)
