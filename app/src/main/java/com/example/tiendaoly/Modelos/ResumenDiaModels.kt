package com.example.tiendaoly.Modelos

import com.google.gson.annotations.SerializedName

data class ResumenDiaResponse(
    val success: Boolean,
    val message: String?,
    val resumen: ResumenTotales?,
    @SerializedName("lista_ventas") val listaVentas: List<VentaItem>?,
    @SerializedName("lista_retiros") val listaRetiros: List<RetiroItem>?
)

data class ResumenTotales(
    val totalVentas: Double,
    val totalRetiros: Double,
    val saldoFinal: Double
)

data class VentaItem(
    @SerializedName("venta_id") val id: String,
    @SerializedName("fecha_hora") val fecha: String,
    @SerializedName("total") val total: Double,

    @SerializedName("nombre_cajero") val cajero: String // NUEVO CAMPO
)

data class RetiroItem(
    @SerializedName("retiro_id") val id: String,
    @SerializedName("fecha_hora") val fecha: String,
    @SerializedName("monto") val monto: Double,
    @SerializedName("descripcion") val descripcion: String,
    @SerializedName("nombre_cajero") val cajero: String // NUEVO CAMPO
)

