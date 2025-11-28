package com.example.tiendaoly.Modelos

import com.google.gson.annotations.SerializedName

data class WebProd(
    val producto_id: Int,
    val nombre: String,
    val codigo: String,
    val descripcion: String?,
    val precio_venta: Double,
    val precio_compra: Double,
    val stock: Int,
    val categoria_id: Int?,
    val proveedor_id: Int?,
    val fecha_creacion: String,
    val activo: Int,
    @SerializedName(value = "imagenText", alternate = ["ImagenText", "imagen"])
    val imagenText: String?
)