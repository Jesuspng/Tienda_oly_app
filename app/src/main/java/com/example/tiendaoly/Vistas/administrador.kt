package com.example.tiendaoly.Vistas

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tiendaoly.R

class administrador : AppCompatActivity() {

    private lateinit var btnUsuarios: Button
    private lateinit var btnVolver: Button
    private lateinit var btnRegistroGastos: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_administrador)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        btnUsuarios = findViewById(R.id.btnUsuarios)
        btnVolver = findViewById(R.id.btnVolver)
        btnRegistroGastos = findViewById(R.id.btnRegistroGastos)



        btnUsuarios.setOnClickListener{

            val intent = Intent(this, ControlUsuarios::class.java)
            startActivity(intent)

        }


        btnVolver.setOnClickListener{

            val intent = Intent(this, login::class.java)
            startActivity(intent)

        }


        btnRegistroGastos.setOnClickListener{

            val intent = Intent(this, ControlAdmin::class.java)
            startActivity(intent)

        }

    }
}