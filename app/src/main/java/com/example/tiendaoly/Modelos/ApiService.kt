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
    @POST("registrarUsuario.php")
    fun registrarUsuario(
        @Field("alias") alias: String,
        @Field("password") pass: String,
        @Field("nombre") nombre: String,
        @Field("apellido") apellido: String,
        @Field("email") email: String,
        @Field("telefono") telefono: String,
        @Field("direccion") direccion: String,
        @Field("fecha_nacimiento") fechaNacimiento: String
    ):Call<respuestaLogin>



    @FormUrlEncoded
    @POST("login.php")
    fun login(
        @Field("alias")alias: String,
        @Field("contrasena")contrasena: String): Call<respuestaLogin>
}