package com.example.tiendaoly.Adaptadores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tiendaoly.Modelos.VentaItem
import com.example.tiendaoly.R
import java.util.Locale

class AdapterVentas(private val lista: List<VentaItem>) : RecyclerView.Adapter<AdapterVentas.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvId: TextView = view.findViewById(R.id.tvidVenta)
        val tvFecha: TextView = view.findViewById(R.id.tvfechaVenta)
        val tvUsuario: TextView = view.findViewById(R.id.tvusuarioVenta)
        val tvTotal: TextView = view.findViewById(R.id.tvtotalVenta)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_venta, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = lista[position]

        holder.tvId.text = "ID: ${item.id}"

        holder.tvFecha.text = "Fecha: ${item.fecha}"

        holder.tvUsuario.text = "Atendió: ${item.cajero}"
        val totalFormateado = String.format(Locale.US, "%.2f", item.total)
        holder.tvTotal.text = "Total: $$totalFormateado"
    }

    override fun getItemCount(): Int {
        return lista.size
    }
}