package com.example.tiendaoly.Vistas

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tiendaoly.Modelos.ApiService
import com.example.tiendaoly.Modelos.WebProd
import com.example.tiendaoly.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient

class ProductoWebView : AppCompatActivity() {

    private lateinit var rcvLista: RecyclerView
    private lateinit var service: ApiService
    private lateinit var searchView: SearchView
    private lateinit var adaptador: ProductoAdapterWeb

    // Lista auxiliar para no perder los datos al buscar
    private var listaOriginal: List<WebProd> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_producto_web_view)

        // Inicializar
        rcvLista = findViewById(R.id.rcvCafeWeb)
        searchView = findViewById(R.id.svBusqueda)
        rcvLista.layoutManager = LinearLayoutManager(this)

        // Configurar Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://equipo6.grupoahost.com/Api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient())
            .build()

        service = retrofit.create(ApiService::class.java)

        setupBuscador()
        cargarProductosWeb()
    }

    private fun setupBuscador() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                filtrar(newText ?: "")
                return true
            }
        })
    }

    private fun filtrar(texto: String) {
        // PROTECCIÓN: Si la lista original está vacía, no hacemos nada (evita errores)
        if (listaOriginal.isEmpty()) return

        val listaFiltrada = if (texto.isEmpty()) {
            // CASO 1: Si no hay texto, mostramos TODO la lista original
            listaOriginal
        } else {
            // CASO 2: Si hay texto, filtramos
            listaOriginal.filter { producto ->
                producto.nombre.lowercase().contains(texto.lowercase())
            }
        }

        // Actualizamos el adaptador
        if (::adaptador.isInitialized) {
            adaptador.actualizarLista(listaFiltrada)
        }
    }

    private fun cargarProductosWeb() {
        service.getCafes().enqueue(object : Callback<List<WebProd>> {
            override fun onResponse(call: Call<List<WebProd>>, response: Response<List<WebProd>>) {
                if (response.isSuccessful) {
                    val listaDelServidor = response.body()

                    if (!listaDelServidor.isNullOrEmpty()) {

                        listaOriginal = listaDelServidor

                        adaptador = ProductoAdapterWeb(this@ProductoWebView, listaOriginal)
                        rcvLista.adapter = adaptador

                        android.util.Log.d("DEBUG_APP", "Llegaron ${listaOriginal.size} productos")

                    } else {
                        Toast.makeText(baseContext, "El servidor devolvió 0 productos", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(baseContext, "Error del servidor: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<WebProd>>, t: Throwable) {
                Toast.makeText(baseContext, "Fallo de red: ${t.message}", Toast.LENGTH_LONG).show()
                android.util.Log.e("DEBUG_APP", "Error: ${t.message}")
            }
        })
    }
}