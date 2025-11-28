package com.example.tiendaoly.Vistas

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.tiendaoly.R

class DetalleWebActivity : AppCompatActivity() {

    private lateinit var nombre: TextView
    private lateinit var descripcion: TextView
    private lateinit var precio: TextView
    private lateinit var stock: TextView
    private lateinit var BtnVolver: Button
    private lateinit var categoria: TextView
    private lateinit var imagen: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_web)


        nombre = findViewById(R.id.txvNombreDetalle)
        descripcion = findViewById(R.id.txvDescripcionDetalle)
        precio = findViewById(R.id.txvPrecioDetalle)
        stock = findViewById(R.id.txvStockDetalle)
        categoria = findViewById(R.id.txvCategoriaDetalle)
        imagen = findViewById(R.id.imgDetalle)

        BtnVolver = findViewById(R.id.BtnVolver)


        val nombreDetalle = intent.getStringExtra("nombre") ?: "Sin nombre"
        val descripcionDetalle = intent.getStringExtra("descripcion") ?: "Sin descripción"
        val imagenDetalle = intent.getStringExtra("imagen") ?: ""


        val precioDetalle = intent.getDoubleExtra("precio", 0.0)
        val stockDetalle = intent.getIntExtra("stock", 0)
        val categoriaDetalle = intent.getIntExtra("categoria", 0)



        if (imagenDetalle.isNotEmpty()) {
            Glide.with(this)
                .load("https://equipo6.grupoahost.com/img/" + imagenDetalle.trim())
                .placeholder(R.drawable.logo)
                .into(imagen)
        }


        nombre.text = nombreDetalle
        descripcion.text = descripcionDetalle
        precio.text = "$ $precioDetalle"
        stock.text = "Stock: $stockDetalle"
        categoria.text = "Categoría ID: $categoriaDetalle"

        BtnVolver.setOnClickListener{

            val intent = Intent(this, ProductoWebView::class.java)
            startActivity(intent)

        }
    }
}