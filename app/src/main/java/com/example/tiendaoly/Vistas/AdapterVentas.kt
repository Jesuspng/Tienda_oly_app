package com.example.tiendaoly.Adaptadores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tiendaoly.Modelos.VentaItem
import com.example.tiendaoly.R

class AdapterVentas(private var lista: List<VentaItem>) : RecyclerView.Adapter<AdapterVentas.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtId: TextView = view.findViewById(R.id.tvidVenta)
        val txtUsuario: TextView = view.findViewById(R.id.tvusuarioVenta)
        val txtTotal: TextView = view.findViewById(R.id.tvtotalVenta)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_venta, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = lista[position]
        holder.txtId.text = "Venta #${item.id}"
        holder.txtUsuario.text = "Usuario ID: ${item.usuarioId} | ${item.fecha}"
        holder.txtTotal.text = "$${item.total}"
    }

    override fun getItemCount() = lista.size

    // Función para actualizar la lista desde la Activity
    fun actualizarLista(nuevaLista: List<VentaItem>) {
        lista = nuevaLista
        notifyDataSetChanged()
    }
}