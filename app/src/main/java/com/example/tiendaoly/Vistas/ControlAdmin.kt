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
import com.example.tiendaoly.Modelos.ResumenDiaResponse
import com.example.tiendaoly.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ControlAdmin : AppCompatActivity() {

    // Variables para la interfaz
    private lateinit var btnVolverAdmin: Button
    private lateinit var txtTotalVentas: TextView
    private lateinit var txtTotalRetiros: TextView
    private lateinit var txtSaldoFinal: TextView

    // Variable para la API
    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_control_admin)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 1. Inicializar Vistas
        btnVolverAdmin = findViewById(R.id.btnVolverAdmin)
        txtTotalVentas = findViewById(R.id.txtTotalVentas)   // Asegúrate de tener este ID en el XML
        txtTotalRetiros = findViewById(R.id.txtTotalRetiros) // Asegúrate de tener este ID en el XML
        txtSaldoFinal = findViewById(R.id.txtSaldoFinal)     // Asegúrate de tener este ID en el XML

        // 2. Inicializar Retrofit (Conexión API)
        val retrofit = Retrofit.Builder()
            .baseUrl("https://equipo6.grupoahost.com/Api/") // Tu URL base
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)

        // 3. Botón Volver
        btnVolverAdmin.setOnClickListener{
            val intent = Intent(this, administrador::class.java)
            startActivity(intent)
            finish() // Cierra esta ventana para no acumularlas
        }

        // 4. Cargar datos automáticamente al abrir
        cargarDatosDelDia()
    }

    private fun cargarDatosDelDia() {
        apiService.obtenerResumenDia().enqueue(object : Callback<ResumenDiaResponse> {
            override fun onResponse(call: Call<ResumenDiaResponse>, response: Response<ResumenDiaResponse>) {
                if (response.isSuccessful) {
                    val datos = response.body()

                    if (datos != null && datos.success) {
                        // Extraer valores del objeto 'resumen'
                        val ventas = datos.resumen.totalVentas
                        val retiros = datos.resumen.totalRetiros
                        val saldo = datos.resumen.saldoFinal

                        // Asignar a los TextViews
                        txtTotalVentas.text = "Ventas: $${ventas}"
                        txtTotalRetiros.text = "Retiros: $${retiros}"
                        txtSaldoFinal.text = "Saldo: $${saldo}"

                        // Opcional: Aquí podrías llenar un RecyclerView con datos.listaVentas
                    } else {
                        Toast.makeText(this@ControlAdmin, "No se pudieron cargar los datos", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@ControlAdmin, "Error de servidor", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResumenDiaResponse>, t: Throwable) {
                Toast.makeText(this@ControlAdmin, "Fallo de conexión: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })


    }
}