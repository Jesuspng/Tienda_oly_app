package com.example.tiendaoly.Modelos

import retrofit2.http.GET
import retrofit2.Call
interface ifaceApiService {
    @GET("serviceProd.php")
    fun readPro(): Call<List<WebProd>>

}