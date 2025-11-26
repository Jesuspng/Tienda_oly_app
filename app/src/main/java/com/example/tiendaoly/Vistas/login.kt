package com.example.tiendaoly.Vistas

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tiendaoly.Modelos.ApiService
import com.example.tiendaoly.Modelos.respuestaLogin
import com.example.tiendaoly.R
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class login : AppCompatActivity() {

    private lateinit var edtAlias: EditText
    private lateinit var edtPassword: EditText
    private lateinit var loginButton: Button
    private lateinit var BtnRegistrarUsuario: Button
    private lateinit var loginService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializamos vistas
        edtAlias = findViewById(R.id.Alias)
        edtPassword = findViewById(R.id.TxtPass)
        loginButton = findViewById(R.id.btnIniciar)
        BtnRegistrarUsuario = findViewById(R.id.crearcuenta)

        // Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://equipo6.grupoahost.com/Api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        loginService = retrofit.create(ApiService::class.java)


        BtnRegistrarUsuario.setOnClickListener{

            val intent = Intent(this, registrarUsuario::class.java)
            startActivity(intent)

        }

        // Acción del botón
        loginButton.setOnClickListener {
            val alias = edtAlias.text.toString().trim()
            val password = edtPassword.text.toString().trim()

            if (alias.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Llena todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            login(alias, password)
        }
    }

    private fun login(alias: String, password: String) {

        loginService.login(alias, password).enqueue(object : Callback<respuestaLogin> {

            override fun onResponse(
                call: Call<respuestaLogin>,
                response: Response<respuestaLogin>
            ) {
                val resp = response.body()

                if (response.isSuccessful && resp != null && resp.success == true) {


                    navegarInicio(resp.alias, resp.rol)

                } else {
                    Toast.makeText(
                        this@login,
                        resp?.message ?: "Usuario o contraseña incorrectos",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<respuestaLogin>, t: Throwable) {
                Toast.makeText(this@login, "Error al conectar: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun navegarInicio(alias: String?, rol: String?) {

        val intent = when (rol) {

            "Admin" -> Intent(this, administrador::class.java)

            "Cliente" -> Intent(this, ProductoWebView::class.java)

            "Cajero" -> Intent(this, administrador::class.java)

            else -> {
                Toast.makeText(this, "Rol desconocido: $rol", Toast.LENGTH_SHORT).show()
                return
            }
        }

        intent.putExtra("ALIAS", alias)
        intent.putExtra("ROL", rol)

        startActivity(intent)
        finish()
    }



}
