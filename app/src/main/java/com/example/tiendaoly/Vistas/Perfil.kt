package com.example.tiendaoly.Vistas

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tiendaoly.Modelos.ApiService
import com.example.tiendaoly.Modelos.PerfilResponse
import com.example.tiendaoly.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Perfil : AppCompatActivity() {


    private var rawNombre: String = ""
    private var rawApellido: String = ""
    private var rawTelefono: String = ""
    private var rawDireccion: String = ""
    private var rawFechaNac: String = ""
    private var currentId: String = ""
    private lateinit var tvAliasProfile: TextView
    private lateinit var tvNombreCompleto: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvTelefono: TextView
    private lateinit var tvDireccion: TextView
    private lateinit var tvFecha: TextView
    private lateinit var btnCerrarSesion: Button
    private lateinit var btnEditar: Button
    private lateinit var btnVolver: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_perfil)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tvAliasProfile = findViewById(R.id.tvAliasProfile)
        tvNombreCompleto = findViewById(R.id.tvNombreProfile)
        tvEmail = findViewById(R.id.tvEmailProfile)
        tvTelefono = findViewById(R.id.tvTelefonoProfile)
        tvDireccion = findViewById(R.id.tvDireccionProfile)
        tvFecha = findViewById(R.id.tvNacimientoProfile)
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion)
        btnEditar = findViewById(R.id.btnEditarPerfil)
        btnVolver = findViewById(R.id.BtnVolver)


        val sharedPref = getSharedPreferences("SesionTienda", MODE_PRIVATE)
        val idUsuario = sharedPref.getString("USER_ID", null)

        if (idUsuario != null) {
            cargarDatosDelPerfil(idUsuario)
        } else {
            irALogin()
        }
        btnCerrarSesion.setOnClickListener {
            val editor = sharedPref.edit()
            editor.clear()
            editor.apply()
            irALogin()
        }

        btnVolver.setOnClickListener {
            val intent = Intent(this, ProductoWebView::class.java)
            startActivity(intent)
        }

        btnEditar.setOnClickListener {
            val intent = Intent(this, EditarUsuario::class.java)


            intent.putExtra("ID", currentId)
            intent.putExtra("ALIAS", tvAliasProfile.text.toString()) // El alias lo sacamos del textview directo
            intent.putExtra("EMAIL", tvEmail.text.toString())


            intent.putExtra("NOMBRE", rawNombre)
            intent.putExtra("APELLIDO", rawApellido)
            intent.putExtra("TELEFONO", rawTelefono)
            intent.putExtra("DIRECCION", rawDireccion)
            intent.putExtra("FECHA", rawFechaNac)

            startActivity(intent)
        }
    }

    private fun cargarDatosDelPerfil(id: String) {

        currentId = id

        val retrofit = Retrofit.Builder()
            .baseUrl("https://equipo6.grupoahost.com/Api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ApiService::class.java)

        service.obtenerPerfil(id).enqueue(object : Callback<PerfilResponse> {
            override fun onResponse(call: Call<PerfilResponse>, response: Response<PerfilResponse>) {
                if (response.isSuccessful) {
                    val datos = response.body()

                    if (datos != null && datos.success && datos.perfil != null) {
                        val p = datos.perfil

                        rawNombre = p.nombre
                        rawApellido = p.apellido
                        rawTelefono = p.telefono ?: ""
                        rawDireccion = p.direccion ?: ""
                        rawFechaNac = p.fechaNacimiento ?: ""


                        tvAliasProfile.text = p.alias
                        tvNombreCompleto.text = "${p.nombre} ${p.apellido}"
                        tvEmail.text = p.email
                        tvTelefono.text = p.telefono ?: "Sin teléfono"
                        tvDireccion.text = p.direccion ?: "Sin dirección"
                        tvFecha.text=p.fechaNacimiento ?: "sin fecha"

                    } else {
                        Toast.makeText(this@Perfil, "No se encontró información del perfil", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@Perfil, "Error del servidor", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<PerfilResponse>, t: Throwable) {
                Toast.makeText(this@Perfil, "Error de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun irALogin() {
        val intent = Intent(this, login::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}