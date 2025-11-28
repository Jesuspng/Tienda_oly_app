package com.example.tiendaoly.Vistas

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tiendaoly.R

class Perfil : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_perfil)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val sharedPref = getSharedPreferences("SesionTienda", MODE_PRIVATE)
        val idUsuario = sharedPref.getString("USER_ID", null)

        if (idUsuario != null) {
            cargarDatosDelPerfil(idUsuario)
        } else {
            val intent = Intent(this, login::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun cargarDatosDelPerfil(id: String) {
        //  Tu lógica para llamar a la API
    }
}