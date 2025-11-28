package com.example.tiendaoly.Presentador


import com.example.tiendaoly.Contratos.ContratoProducto
import com.example.tiendaoly.Modelos.Producto
import com.example.tiendaoly.Modelos.ProductoModelo

class productoPresentador(val vista: ContratoProducto.VistaProd): ContratoProducto.presentadorProd {

    val modelo= ProductoModelo()
    override fun loadData() {
        val lista=modelo.loadProducto()
        vista.loadData(lista)
    }

}