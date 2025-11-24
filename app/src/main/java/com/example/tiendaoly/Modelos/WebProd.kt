package com.example.tiendaoly.Modelos

import com.google.gson.annotations.SerializedName

data class WebProd(
    // Si en tu base de datos la columna se llama "id", pero aquí quieres usar "producto_id"
    // @SerializedName("id")
    val producto_id: Int,

    val nombre: String,
    val codigo: String,

    // Está bien que sea nullable
    val descripcion: String?,

    val precio_venta: Double,
    val precio_compra: Double,
    val stock: Int,

    val categoria_id: String,
    val proveedor_id: Int?,
    val fecha_creacion: String,
    val activo: Int,

    // CORRECCIÓN CRÍTICA:
    // 1. SerializedName asegura que coincida con el PHP (si el PHP envía "ImagenText" o "imagen_text")
    // 2. String? (con signo de interrogación) evita que la app se cierre si viene vacío
    @SerializedName(value = "imagenText", alternate = ["ImagenText", "imagen"])
    val imagenText: String?
)