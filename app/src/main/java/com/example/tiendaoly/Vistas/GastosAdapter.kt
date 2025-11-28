package com.example.tiendaoly.Vistas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tiendaoly.Modelos.RetiroItem
import com.example.tiendaoly.R
import java.util.Locale

class GastosAdapter(private val lista: List<RetiroItem>) : RecyclerView.Adapter<GastosAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvId: TextView = view.findViewById(R.id.tvIdRetiro)
        val tvFecha: TextView = view.findViewById(R.id.tvFechaRetiro)
        val tvUsuario: TextView = view.findViewById(R.id.tvUsuarioRetiro)
        val tvMonto: TextView = view.findViewById(R.id.txtMontoGasto)
        val tvDesc: TextView = view.findViewById(R.id.txtDescGasto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_gasto, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = lista[position]


        holder.tvId.text = "ID Retiro: ${item.id}"


        val fechaMostrar = if (item.fecha.length > 10) item.fecha.substring(0, 16) else item.fecha
        holder.tvFecha.text = "Fecha: $fechaMostrar"

        holder.tvUsuario.text = "Usuario: ${item.cajero}"

        holder.tvDesc.text = "Descripción: ${item.descripcion}"


        val montoFormateado = String.format(Locale.US, "%.2f", item.monto)
        holder.tvMonto.text = "Monto: -$$montoFormateado"
    }

    override fun getItemCount() = lista.size
}