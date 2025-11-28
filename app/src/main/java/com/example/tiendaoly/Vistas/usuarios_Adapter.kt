package com.example.tiendaoly.Vistas

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tiendaoly.Modelos.ApiService
import com.example.tiendaoly.Modelos.UsuarioResponse
import com.example.tiendaoly.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ControlUsuarios : AppCompatActivity() {

    private lateinit var btnVolver: Button
    private lateinit var rcvUsuarios: RecyclerView
    private lateinit var apiService: ApiService

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
        rcvUsuarios = findViewById(R.id.rcvUsuarios)


        rcvUsuarios.layoutManager = LinearLayoutManager(this)


        btnVolver.setOnClickListener{
            val intent = Intent(this, administrador::class.java)
            startActivity(intent)
            finish()
        }


        val retrofit = Retrofit.Builder()
            .baseUrl("https://equipo6.grupoahost.com/Api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)


        cargarUsuarios()
    }

    private fun cargarUsuarios() {
        apiService.obtenerUsuarios().enqueue(object : Callback<UsuarioResponse> {
            override fun onResponse(call: Call<UsuarioResponse>, response: Response<UsuarioResponse>) {
                if (response.isSuccessful) {
                    val datos = response.body()
                    if (datos != null && datos.success) {

                        rcvUsuarios.adapter = UsuariosAdapter(datos.usuarios ?: emptyList())
                    } else {
                        Toast.makeText(this@ControlUsuarios, "No hay usuarios", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@ControlUsuarios, "Error servidor", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UsuarioResponse>, t: Throwable) {
                Toast.makeText(this@ControlUsuarios, "Error conexión: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}