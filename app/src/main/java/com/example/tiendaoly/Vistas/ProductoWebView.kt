package com.example.tiendaoly.Vistas

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tiendaoly.Contratos.ContratoProducto
import com.example.tiendaoly.Modelos.ApiService
import com.example.tiendaoly.Modelos.Producto
import com.example.tiendaoly.Modelos.WebProd
import com.google.gson.internal.GsonBuildConfig
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.tiendaoly.R



class ProductoWebView : AppCompatActivity(), ContratoProducto.VistaProd {
    private lateinit var rcvLista: RecyclerView
    private lateinit var service: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_producto_web_view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        rcvLista = findViewById(R.id.rcvCafeWeb)
        rcvLista.layoutManager = LinearLayoutManager(this)

        //configuramos retrofit //esto iria en el modelo
        val retrofit = Retrofit.Builder()
            // IMPORTANTE: Asegúrate de si existe la carpeta "/api/" o no.
            // Si tus archivos php están sueltos, borra "api/".
            .baseUrl("https://equipo6.grupoahost.com/Api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient())
            .build()

        service = retrofit.create(ApiService::class.java)
        cargarProductosWeb()
    }

    private fun cargarProductosWeb() {
        // Ahora usamos 'getCafes()' porque así se llama en tu interfaz ApiService
        service.getCafes().enqueue(object : retrofit2.Callback<List<WebProd>> {

            override fun onResponse(call: Call<List<WebProd>>, response: Response<List<WebProd>>) {
                if (response.isSuccessful) {
                    val lista = response.body()
                    if (!lista.isNullOrEmpty()) {
                        // Pasamos la lista al adaptador
                        val adaptador = ProductoAdapterWeb(this@ProductoWebView, lista)
                        rcvLista.adapter = adaptador
                    } else {
                        Toast.makeText(baseContext, "No hay productos disponibles", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(baseContext, "Error del servidor: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(
                call: Call<List<WebProd>?>,
                t: Throwable
            ) {
                Toast.makeText(baseContext, "Error al cargar los datos ${t.message}", Toast.LENGTH_SHORT).show()
            }

        })



    }

    override fun loadData(Data: List<Producto>) {
        TODO("Not yet implemented")
    }
}