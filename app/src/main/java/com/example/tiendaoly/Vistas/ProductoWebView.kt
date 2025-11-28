package com.example.tiendaoly.Vistas

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
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

    private lateinit var usuario: ImageView
    private var listaOriginal: List<WebProd> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_producto_web_view)


        rcvLista = findViewById(R.id.rcvCafeWeb)
        searchView = findViewById(R.id.svBusqueda)
        rcvLista.layoutManager = LinearLayoutManager(this)
        usuario= findViewById(R.id.usuario)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://equipo6.grupoahost.com/Api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient())
            .build()

        service = retrofit.create(ApiService::class.java)

        setupBuscador()
        cargarProductosWeb()

        usuario.setOnClickListener {

            val intent = Intent(this, Perfil::class.java)
            startActivity(intent)
        }
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

        if (listaOriginal.isEmpty()) return

        val listaFiltrada = if (texto.isEmpty()) {

            listaOriginal
        } else {

            listaOriginal.filter { producto ->
                producto.nombre.lowercase().contains(texto.lowercase())
            }
        }


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