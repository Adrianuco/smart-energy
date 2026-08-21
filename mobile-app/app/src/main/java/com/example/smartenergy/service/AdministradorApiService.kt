package com.example.smartenergy.service

import com.example.smartenergy.model.Administrador
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import java.util.UUID

interface AdministradorApiService {
    @GET("administrador/all")
    suspend fun obtenerAdministradors(): Response<List<Administrador>>

    @GET("administrador/{id}")
    suspend fun obtenerAdministradorPorId(@Path("id") id: UUID): Response<Administrador>

    @POST("administrador/save")
    suspend fun guardarAdministrador(@Body administrador: Administrador): Response<Administrador>

    @PUT("administrador/update")
    suspend fun actualizarAdministrador(@Body administrador: Administrador): Response<Administrador>
}