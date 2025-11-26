package com.example.tiendaoly.Modelos

import com.google.gson.annotations.SerializedName

// Respuesta principal de la API
data class ResumenDiaResponse(
    val success: Boolean,
    val resumen: ResumenTotales,
    @SerializedName("lista_ventas") val listaVentas: List<VentaItem>,
    @SerializedName("lista_retiros") val listaRetiros: List<RetiroItem>
)

// Objeto para los totales numéricos
data class ResumenTotales(
    @SerializedName("total_ventas") val totalVentas: Double,
    @SerializedName("total_retiros") val totalRetiros: Double,
    @SerializedName("saldo_final") val saldoFinal: Double
)

// Objeto para cada venta de la lista
data class VentaItem(
    @SerializedName("venta_id") val id: Int,
    @SerializedName("fecha_hora") val fecha: String,
    @SerializedName("usuario_id") val usuarioId: Int,
    val total: Double
)

// Objeto para cada retiro de la lista
data class RetiroItem(
    @SerializedName("retiro_id") val id: Int,
    @SerializedName("fecha_hora") val fecha: String,
    @SerializedName("usuario_id") val usuarioId: Int,
    val monto: Double,
    val descripcion: String
)