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
import com.example.tiendaoly.Modelos.GenericResponse
import com.example.tiendaoly.Modelos.PerfilUpdateRequest
import com.example.tiendaoly.Modelos.ApiService
import com.example.tiendaoly.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EditarUsuario : AppCompatActivity() {

    private lateinit var edtAlias: EditText
    private lateinit var edtNombre: EditText
    private lateinit var edtApellido: EditText
    private lateinit var edtCorreo: EditText
    private lateinit var edtTelefono: EditText
    private lateinit var edtDireccion: EditText
    private lateinit var edtFechaNac: EditText
    private lateinit var edtContrasena: EditText

    private lateinit var btnActualizar: Button
    private lateinit var btnVolver: Button

    private var idUsuario: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_editar_usuario)

        edtAlias = findViewById(R.id.edtAlias)
        edtNombre = findViewById(R.id.edtNombre)
        edtApellido = findViewById(R.id.edtApellido)
        edtCorreo = findViewById(R.id.edtCorreo)
        edtTelefono = findViewById(R.id.edtTelefono)
        edtDireccion = findViewById(R.id.edtDireccion)
        edtFechaNac = findViewById(R.id.edtFechaNac)
        edtContrasena = findViewById(R.id.edtContrasena)

        btnActualizar = findViewById(R.id.btnActualizar)
        btnVolver = findViewById(R.id.btnVolver)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val extras = intent.extras
        if (extras != null) {

            idUsuario = extras.getString("ID")

            edtAlias.setText(extras.getString("ALIAS"))
            edtNombre.setText(extras.getString("NOMBRE"))
            edtApellido.setText(extras.getString("APELLIDO"))
            edtCorreo.setText(extras.getString("EMAIL"))
            edtTelefono.setText(extras.getString("TELEFONO"))
            edtDireccion.setText(extras.getString("DIRECCION"))
            edtFechaNac.setText(extras.getString("FECHA"))
        }

        btnVolver.setOnClickListener {
            finish()
        }

        btnActualizar.setOnClickListener {

            if (edtAlias.text.isEmpty() || edtCorreo.text.isEmpty()) {
                Toast.makeText(this, "Alias y Correo son obligatorios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val retrofit = Retrofit.Builder()
                .baseUrl("https://equipo6.grupoahost.com/Api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service = retrofit.create(ApiService::class.java)

            val datosActualizados = PerfilUpdateRequest(
                id = idUsuario ?: "",
                alias = edtAlias.text.toString(),
                nombre = edtNombre.text.toString(),
                apellido = edtApellido.text.toString(),
                email = edtCorreo.text.toString(),
                telefono = edtTelefono.text.toString(),
                direccion = edtDireccion.text.toString(),
                fecha_nacimiento = edtFechaNac.text.toString()
            )

            service.actualizarPerfil(datosActualizados).enqueue(object : Callback<GenericResponse> {
                override fun onResponse(call: Call<GenericResponse>, response: Response<GenericResponse>) {
                    if (response.isSuccessful && response.body()?.success == true) {
                        Toast.makeText(this@EditarUsuario, "¡Datos actualizados!", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this@EditarUsuario, Perfil::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)
                        finish()

                    } else {
                        Toast.makeText(
                            this@EditarUsuario,
                            response.body()?.message ?: "Error al actualizar",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<GenericResponse>, t: Throwable) {
                    Toast.makeText(this@EditarUsuario, "Error de red: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}