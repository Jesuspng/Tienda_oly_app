package com.example.tiendaoly.Vistas

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tiendaoly.Modelos.ApiService
import com.example.tiendaoly.Modelos.respuestaLogin
import com.example.tiendaoly.R
import java.util.Calendar
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class registrarUsuario : AppCompatActivity() {

    private lateinit var apiService: ApiService

    private lateinit var edtAlias: EditText
    private lateinit var edtNombre: EditText
    private lateinit var edtApellido: EditText
    private lateinit var edtCorreo: EditText
    private lateinit var edtTelefono: EditText
    private lateinit var edtDireccion: EditText
    private lateinit var edtFechaNac: EditText
    private lateinit var edtPassword: EditText

    private lateinit var btncrearcuenta: Button
    private lateinit var btnVolver: Button
    val calendario = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_usuario)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://equipo6.grupoahost.com/Api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)


        edtAlias = findViewById(R.id.edtAlias)
        edtNombre = findViewById(R.id.edtNombre)
        edtApellido = findViewById(R.id.edtApellido)
        edtCorreo = findViewById(R.id.edtCorreo)
        edtTelefono = findViewById(R.id.edtTelefono)
        edtDireccion = findViewById(R.id.edtDireccion)
        edtFechaNac = findViewById(R.id.edtFechaNac)
        edtPassword = findViewById(R.id.TxtPass)

        btncrearcuenta = findViewById(R.id.btncrearcuenta)
        btnVolver = findViewById(R.id.btnVolver)


        edtFechaNac.setOnClickListener {
            val year = calendario.get(Calendar.YEAR)
            val month = calendario.get(Calendar.MONTH)
            val day = calendario.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(this, { _, y, m, d ->
                // AAAA-MM-DD
                val fecha = "%04d-%02d-%02d".format(y, m + 1, d)
                edtFechaNac.setText(fecha)
            }, year, month, day)

            datePicker.show()
        }

        btncrearcuenta.setOnClickListener {
            registrar()
        }

        btnVolver.setOnClickListener {
            finish()
        }
    }

    private fun registrar() {
        val alias = edtAlias.text.toString().trim()
        val nombre = edtNombre.text.toString().trim()
        val apellido = edtApellido.text.toString().trim()
        val email = edtCorreo.text.toString().trim()
        val telefono = edtTelefono.text.toString().trim()
        val direccion = edtDireccion.text.toString().trim()
        val fechaNac = edtFechaNac.text.toString().trim()
        val password = edtPassword.text.toString().trim()

        if (alias.isEmpty() || nombre.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Completa los campos obligatorios", Toast.LENGTH_SHORT).show()
            return
        }

        apiService.registrarUsuario(
            alias,
            password,
            nombre,
            apellido,
            email,
            telefono,
            direccion,
            fechaNac
        ).enqueue(object : Callback<respuestaLogin> {

            override fun onResponse(
                call: Call<respuestaLogin>,
                response: Response<respuestaLogin>
            ) {
                val resp = response.body()

                if (response.isSuccessful && resp != null) {
                    if (resp.success) {
                        Toast.makeText(
                            this@registrarUsuario,
                            "Cuenta creada correctamente",
                            Toast.LENGTH_LONG
                        ).show()

                        startActivity(Intent(this@registrarUsuario, login::class.java))
                        finish()
                    } else {
                        Toast.makeText(this@registrarUsuario, resp.message, Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(this@registrarUsuario, "Error en la respuesta", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<respuestaLogin>, t: Throwable) {
                Toast.makeText(
                    this@registrarUsuario,
                    "Error: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}