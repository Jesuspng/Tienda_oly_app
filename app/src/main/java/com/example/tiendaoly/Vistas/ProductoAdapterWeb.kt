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


class ProductoAdapterWeb(val contexto: Context, var catalogo: List<WebProd>) :
    RecyclerView.Adapter<ProductoAdapterWeb.ViewHolderWeb>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderWeb {

        val view = LayoutInflater.from(contexto).inflate(R.layout.item_prod_web, parent, false)
        return ViewHolderWeb(view)
    }

    override fun onBindViewHolder(holder: ViewHolderWeb, position: Int) {
        val produc = catalogo[position]


        holder.nombreweb.text = produc.nombre
        holder.puntuacion.text = "$ ${produc.precio_venta}"

        val nombreImagen = produc.imagenText?.trim() ?: ""
        val urlImagen = "https://equipo6.grupoahost.com/img/" + nombreImagen


        Glide.with(contexto)
            .load(urlImagen)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_foreground)
            .into(holder.imagen)


        holder.imagen.setOnClickListener {
            verDetalle(produc)
        }
    }

    override fun getItemCount(): Int {
        return catalogo.size
    }


    fun actualizarLista(nuevaLista: List<WebProd>) {
        catalogo = nuevaLista
        notifyDataSetChanged()
    }


    private fun verDetalle(producto: WebProd) {
        val intent = Intent(contexto, DetalleWebActivity::class.java).apply {

            putExtra("nombre", producto.nombre)
            putExtra("descripcion", producto.descripcion)
            putExtra("imagen", producto.imagenText)
            putExtra("codigo", producto.codigo)


            putExtra("stock", producto.stock)
            putExtra("categoria", producto.categoria_id)
            putExtra("precio", producto.precio_venta)
        }
        contexto.startActivity(intent)
    }


    class ViewHolderWeb(control: View) : RecyclerView.ViewHolder(control) {
        val nombreweb: TextView = control.findViewById(R.id.txvNombreweb)
        val puntuacion: TextView = control.findViewById(R.id.txvPuntuacionweb)
        val imagen: ImageView = control.findViewById(R.id.imgfotoweb)
    }
}