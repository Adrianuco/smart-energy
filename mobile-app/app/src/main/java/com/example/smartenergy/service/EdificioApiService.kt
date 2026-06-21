package com.example.smartenergy.service

import com.example.smartenergy.model.DetalleEdificio
import com.example.smartenergy.model.Edificio
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import java.util.UUID

interface EdificioApiService {
    @GET("edificio/all")
    suspend fun obtenerEdificios(): Response<List<Edificio>>

    @GET("edificio/{id}")
    suspend fun obtenerEdificioPorId(@Path("id") id: UUID): Response<Edificio>

    @POST("edificio/save")
    suspend fun guardarEdificio(@Body edificio: Edificio): Response<Edificio>

    @PUT("edificio/update")
    suspend fun actualizarEdificio(@Body edificio: Edificio): Response<Edificio>

    @GET("edificio/detalle/{id}")
    suspend fun obtenerDetalle(@Path("id") id: UUID): Response<DetalleEdificio>
}