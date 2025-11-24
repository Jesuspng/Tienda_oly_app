package com.example.tiendaoly.Vistas

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tiendaoly.Modelos.WebProd
import com.example.tiendaoly.R

class ProductoAdapterWeb(val contexto: Context, val catalogo: List<WebProd>) :
    RecyclerView.Adapter<ProductoAdapterWeb.ViewHolderWeb>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderWeb {
        val view = LayoutInflater.from(contexto).inflate(R.layout.item_prod_web, parent, false)
        return ViewHolderWeb(view)
    }

    override fun onBindViewHolder(holder: ViewHolderWeb, position: Int) {
        val produc = catalogo[position]

        holder.nombreweb.text = produc.nombre
        // Agregué el signo $ para mejor presentación
        holder.puntuacion.text = "$ ${produc.precio_venta}"

        // MEJORA: Construcción de URL más segura
        // 1. Usamos trim() por si la BD trae espacios en el nombre (ej: " foto.jpg")
        // SEGURO: Si es null, usa cadena vacía y Glide pondrá la imagen de error.
        val nombreImagen = produc.imagenText?.trim() ?: ""
        val urlImagen = "https://equipo6.grupoahost.com/img/" + nombreImagen

        Glide.with(contexto)
            .load(urlImagen)
            .placeholder(R.drawable.ic_launcher_background) // Imagen mientras carga
            .error(R.drawable.ic_launcher_foreground)       // Imagen si falla (ej. 404)
            .into(holder.imagen)

        holder.imagen.setOnClickListener {
            verDetalle(produc)
        }
    }

    override fun getItemCount(): Int {
        return catalogo.size
    }

    private fun verDetalle(producto: WebProd) {
        val intent = Intent(contexto, DetalleWebActivity::class.java).apply {
            // CORRECCIÓN: Estandaricé las claves a minúsculas y corregí "stok"
            putExtra("nombre", producto.nombre)
            putExtra("descripcion", producto.descripcion) // Antes "Descripcion"
            putExtra("imagen", producto.imagenText)
            putExtra("codigo", producto.codigo)
            putExtra("stock", producto.stock)             // Antes "stok"
            putExtra("categoria", producto.categoria_id)  // Antes "Categoria"
            putExtra("precio", producto.precio_venta)
        }
        contexto.startActivity(intent)
    }

    class ViewHolderWeb(control: View) : RecyclerView.ViewHolder(control) {
        // Asegúrate que estos IDs existen en item_prod_web.xml
        val nombreweb: TextView = control.findViewById(R.id.txvNombreweb)
        val puntuacion: TextView = control.findViewById(R.id.txvPuntuacionweb)
        val imagen: ImageView = control.findViewById(R.id.imgfotoweb)
    }
}