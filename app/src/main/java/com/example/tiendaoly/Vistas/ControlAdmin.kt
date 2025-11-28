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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tiendaoly.Adaptadores.AdapterVentas
import com.example.tiendaoly.Modelos.ApiService
import com.example.tiendaoly.Modelos.ResumenDiaResponse
import com.example.tiendaoly.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Locale

class ControlAdmin : AppCompatActivity() {

    private lateinit var btnVolverAdmin: Button
    private lateinit var txtTotalVentas: TextView
    private lateinit var txtTotalRetiros: TextView
    private lateinit var txtSaldoFinal: TextView

    private lateinit var rcvVentas: RecyclerView
    private lateinit var rcvGastos: RecyclerView

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


        btnVolverAdmin = findViewById(R.id.btnVolverAdmin)
        txtTotalVentas = findViewById(R.id.txtTotalVentas)
        txtTotalRetiros = findViewById(R.id.txtTotalRetiros)
        txtSaldoFinal = findViewById(R.id.txtSaldoFinal)
        rcvVentas = findViewById(R.id.rcvVentas)
        rcvGastos = findViewById(R.id.rcvGastos)


        rcvVentas.layoutManager = LinearLayoutManager(this)
        rcvGastos.layoutManager = LinearLayoutManager(this)


        val retrofit = Retrofit.Builder()
            .baseUrl("https://equipo6.grupoahost.com/Api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)

        btnVolverAdmin.setOnClickListener{
            val intent = Intent(this, administrador::class.java)
            startActivity(intent)
            finish()
        }

        cargarDatosDelDia()
    }

    private fun cargarDatosDelDia() {
        apiService.obtenerResumenDia().enqueue(object : Callback<ResumenDiaResponse> {
            override fun onResponse(call: Call<ResumenDiaResponse>, response: Response<ResumenDiaResponse>) {
                if (response.isSuccessful) {
                    val datos = response.body()

                    if (datos == null) {
                        android.util.Log.e("API_DEBUG", "El cuerpo de la respuesta es NULL.")
                    } else {

                        if (!datos.success) {
                            android.util.Log.e("API_DEBUG", "ERROR DEL SERVIDOR: ${datos.message}")
                        } else {
                            android.util.Log.d("API_DEBUG", "Éxito. Datos recibidos correctamente.")
                        }
                    }


                    if (datos != null && datos.success) {

                        val ventas = datos.resumen?.totalVentas ?: 0.0
                        val retiros = datos.resumen?.totalRetiros ?: 0.0
                        val saldo = datos.resumen?.saldoFinal ?: 0.0

                        txtTotalVentas.text = "Ventas: $${String.format(Locale.US, "%.2f", ventas)}"
                        txtTotalRetiros.text = "Retiros: $${String.format(Locale.US, "%.2f", retiros)}"
                        txtSaldoFinal.text = "Saldo: $${String.format(Locale.US, "%.2f", saldo)}"

                        val listaVentas = datos.listaVentas ?: emptyList()
                        if (listaVentas.isNotEmpty()) {
                            rcvVentas.adapter = AdapterVentas(listaVentas)
                        } else {
                            rcvVentas.adapter = AdapterVentas(emptyList())

                        }

                        val listaRetiros = datos.listaRetiros ?: emptyList()
                        rcvGastos.adapter = GastosAdapter(listaRetiros)

                    } else {
                        Toast.makeText(this@ControlAdmin, "No se pudieron cargar los datos (Ver Logcat)", Toast.LENGTH_SHORT).show()
                    }
                } else {

                    android.util.Log.e("API_DEBUG", "Error HTTP: ${response.code()} ${response.message()}")
                    Toast.makeText(this@ControlAdmin, "Error de servidor: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResumenDiaResponse>, t: Throwable) {
                android.util.Log.e("API_DEBUG", "Fallo conexión: ${t.message}")
                Toast.makeText(this@ControlAdmin, "Fallo de conexión: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}