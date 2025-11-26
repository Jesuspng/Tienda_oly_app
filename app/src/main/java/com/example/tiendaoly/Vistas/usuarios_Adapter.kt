package com.example.tiendaoly.Vistas

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tiendaoly.R

class usuarios_Adapter : AppCompatActivity() {

    private lateinit var btnVolver: Button
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_usuarios_adapter)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnVolver = findViewById(R.id.btnVolver)
        
        btnVolver.setOnClickListener{

            val intent = Intent(this, administrador::class.java)
            startActivity(intent)

        }
    }
}