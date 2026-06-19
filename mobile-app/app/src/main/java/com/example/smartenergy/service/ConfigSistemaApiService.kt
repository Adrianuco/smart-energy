package com.example.smartenergy.service

import com.example.smartenergy.model.ConfigSistema
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import java.util.UUID

interface ConfigSistemaApiService {
    @GET("configuraciones/all")
    suspend fun obtenerConfigSistemas(): Response<List<ConfigSistema>>

    @GET("configuraciones/{id}")
    suspend fun obtenerConfigSistemaPorId(@Path("id") id: UUID): Response<ConfigSistema>

    @POST("configuraciones/save")
    suspend fun guardarConfigSistema(@Body configSistema: ConfigSistema): Response<ConfigSistema>

    @PUT("configuraciones/update")
    suspend fun actualizarConfigSistema(@Body configSistema: ConfigSistema): Response<ConfigSistema>
}