package com.example.tiendaoly.Contratos

import com.example.tiendaoly.Modelos.Producto

interface ContratoProducto {

    interface VistaProd{
        fun loadData(Data: List<Producto>)
    }

    interface presentadorProd{
        fun loadData()
    }

    interface modeloProd{
        fun loadProducto(): List<Producto>
    }
}