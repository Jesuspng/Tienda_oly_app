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

// NOTA: 'catalogo' ahora es 'var' para permitir que el buscador modifique la lista
class ProductoAdapterWeb(val contexto: Context, var catalogo: List<WebProd>) :
    RecyclerView.Adapter<ProductoAdapterWeb.ViewHolderWeb>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderWeb {
        // Inflamos el diseño de la fila
        val view = LayoutInflater.from(contexto).inflate(R.layout.item_prod_web, parent, false)
        return ViewHolderWeb(view)
    }

    override fun onBindViewHolder(holder: ViewHolderWeb, position: Int) {
        val produc = catalogo[position]

        // 1. Asignar textos
        holder.nombreweb.text = produc.nombre
        holder.puntuacion.text = "$ ${produc.precio_venta}" // Formato de precio con signo $

        // 2. Construcción Segura de la URL de la imagen
        // Usamos ?.trim() ?: "" para evitar que la app se cierre si la imagen es null
        val nombreImagen = produc.imagenText?.trim() ?: ""
        val urlImagen = "https://equipo6.grupoahost.com/img/" + nombreImagen

        // 3. Cargar imagen con Glide
        Glide.with(contexto)
            .load(urlImagen)
            .placeholder(R.drawable.ic_launcher_background) // Imagen de espera
            .error(R.drawable.ic_launcher_foreground)       // Imagen si falla o no existe
            .into(holder.imagen)

        // 4. Click en la imagen para ver detalles
        holder.imagen.setOnClickListener {
            verDetalle(produc)
        }
    }

    override fun getItemCount(): Int {
        return catalogo.size
    }


    fun actualizarLista(nuevaLista: List<WebProd>) {
        catalogo = nuevaLista
        notifyDataSetChanged() // Refresca el RecyclerView con los nuevos datos
    }


    private fun verDetalle(producto: WebProd) {
        val intent = Intent(contexto, DetalleWebActivity::class.java).apply {
            // Pasamos los datos con claves en minúsculas para evitar errores
            putExtra("nombre", producto.nombre)
            putExtra("descripcion", producto.descripcion)
            putExtra("imagen", producto.imagenText)
            putExtra("codigo", producto.codigo)

            // IMPORTANTE: Pasamos los números como números (no como String)
            putExtra("stock", producto.stock)             // Int
            putExtra("categoria", producto.categoria_id)  // Int
            putExtra("precio", producto.precio_venta)     // Double
        }
        contexto.startActivity(intent)
    }

    // --- CLASE VIEWHOLDER ---
    // Vincula las variables con los IDs del archivo item_prod_web.xml
    class ViewHolderWeb(control: View) : RecyclerView.ViewHolder(control) {
        val nombreweb: TextView = control.findViewById(R.id.txvNombreweb)
        val puntuacion: TextView = control.findViewById(R.id.txvPuntuacionweb)
        val imagen: ImageView = control.findViewById(R.id.imgfotoweb)
    }
}