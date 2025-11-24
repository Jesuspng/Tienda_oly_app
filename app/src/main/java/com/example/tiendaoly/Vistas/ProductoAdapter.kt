package com.example.tiendaoly.Vistas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tiendaoly.Modelos.Producto
import com.example.tiendaoly.R



class ProductoAdapter(private val productos: List<Producto>) : RecyclerView.Adapter<ProductoAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista= LayoutInflater.from(parent.context)
            .inflate(R.layout.item_prod,parent, false)
        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val prdc = productos[position]
        holder.txvNombre.text = prdc.nombre
        holder.txvPrecio.text = "$${prdc.precio}"
    }

    override fun getItemCount(): Int {
        return productos.size
    }



    class ViewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView){
        val txvNombre= ItemView.findViewById<TextView>(R.id.txvNombre)
        val txvPrecio= ItemView.findViewById<TextView>(R.id.txvPrecio)
        val imgfoto=ItemView.findViewById<ImageView>(R.id.imgfoto)
    }
}

