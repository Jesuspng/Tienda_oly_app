package com.example.tiendaoly.Modelos

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST



interface ApiService {

    @GET("listaProductos.php")
    fun getCafes(): Call<List<WebProd>>

    @FormUrlEncoded
    @POST("registroProd.php")
    fun postProducto(
        @Field("nombre") nombre: String,
        @Field("codigo") codigo: String,
        @Field("descripcion") descripcion: String,
        @Field("precio_venta") precioVenta: Float,
        @Field("precio_compra") precioCompra: Float,
        @Field("stock") stock: Int,
        @Field("categoria_id") categoriaId: Int,
        @Field("proveedor_id") proveedorId: Int,
        @Field("ImagenText") imagenText: String
    ): Call<List<respuestaRegistro>>


    @FormUrlEncoded
    @POST("login.php")
    fun login(
        @Field("alias")alias: String,
        @Field("contrasena")contrasena: String): Call<respuestaLogin>
}