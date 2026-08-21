package com.example.smartenergy.service

import com.example.smartenergy.model.ApoyoLogistico
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import java.util.UUID

interface ApoyoLogisticaApiService {
    @GET("logistica/all")
    suspend fun obtenerApoyoLogisticos(): Response<List<ApoyoLogistico>>

    @GET("logistica/{id}")
    suspend fun obtenerApoyoLogisticoPorId(@Path("id") id: UUID): Response<ApoyoLogistico>

    @POST("logistica/save")
    suspend fun guardarApoyoLogistico(@Body apoyoLogistico: ApoyoLogistico): Response<ApoyoLogistico>

    @PUT("logistica/update")
    suspend fun actualizarApoyoLogistico(@Body apoyoLogistico: ApoyoLogistico): Response<ApoyoLogistico>
}