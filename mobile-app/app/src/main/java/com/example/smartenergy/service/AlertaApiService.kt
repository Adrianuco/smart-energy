package com.example.smartenergy.service

import com.example.smartenergy.model.Alerta
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import java.util.UUID

interface AlertaApiService {
    @GET("alertas/all")
    suspend fun obtenerAlertas(): Response<List<Alerta>>

    @GET("alertas/{id}")
    suspend fun obtenerAlertaPorId(@Path("id") id: UUID): Response<Alerta>

    @POST("alertas/save")
    suspend fun guardarAlerta(@Body alerta: Alerta): Response<Alerta>

    @PUT("alertas/update")
    suspend fun actualizarAlerta(@Body alerta: Alerta): Response<Alerta>
}