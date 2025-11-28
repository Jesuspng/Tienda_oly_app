package com.example.tiendaoly.Vistas

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tiendaoly.Modelos.UsuarioItem
import com.example.tiendaoly.R

class UsuariosAdapter(private val lista: List<UsuarioItem>) : RecyclerView.Adapter<UsuariosAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtId: TextView = view.findViewById(R.id.txtId)
        val txtAlias: TextView = view.findViewById(R.id.txtAlias)
        val txtRol: TextView = view.findViewById(R.id.txtRol)
        val txtActivo: TextView = view.findViewById(R.id.txtActivo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_usuario, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val usuario = lista[position]

        holder.txtId.text = "ID: ${usuario.id}"
        holder.txtAlias.text = "Alias: ${usuario.alias}"
        holder.txtRol.text = "Rol: ${usuario.rol}"

        if (usuario.activo) {
            holder.txtActivo.text = "Activo: Sí"
            holder.txtActivo.setTextColor(Color.GREEN)
        } else {
            holder.txtActivo.text = "Activo: No"
            holder.txtActivo.setTextColor(Color.RED)
        }
    }

    override fun getItemCount(): Int {
        return lista.size
    }
}