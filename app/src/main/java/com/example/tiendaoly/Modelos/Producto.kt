package com.example.tiendaoly.Modelos


data class Producto(
    val foto: String,
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val Inventario: Int,
    val catrgoria: String
)
