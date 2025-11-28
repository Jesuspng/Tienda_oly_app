package com.example.tiendaoly.Modelos

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @GET("listaProductos.php")
    fun getCafes(): Call<List<WebProd>>

    @FormUrlEncoded
    @POST("registroCliente.php")
    fun registrarUsuario(
        @Field("alias") alias: String,
        @Field("password") pass: String,
        @Field("nombre") nombre: String,
        @Field("apellido") apellido: String,
        @Field("email") email: String,
        @Field("telefono") telefono: String,
        @Field("direccion") direccion: String,
        @Field("fecha_nacimiento") fechaNacimiento: String,
    ): Call<respuestaLogin>

    @FormUrlEncoded
    @POST("login.php")
    fun login(
        @Field("alias") alias: String,
        @Field("contrasena") contrasena: String
    ): Call<respuestaLogin>

    @GET("resumenDia.php")
    fun obtenerResumenDia(): Call<ResumenDiaResponse>

    @GET("obtener_usuarios.php")
    fun obtenerUsuarios(): Call<UsuarioResponse>

    @GET("obtener_perfil.php")
    fun obtenerPerfil(@Query("id") id: String): Call<PerfilResponse>

    @POST("actualizar_perfil.php")
    fun actualizarPerfil(@Body request: PerfilUpdateRequest): Call<GenericResponse>
}