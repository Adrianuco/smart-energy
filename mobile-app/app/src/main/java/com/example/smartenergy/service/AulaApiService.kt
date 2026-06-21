package com.example.smartenergy.service

import com.example.smartenergy.model.Aula
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import java.util.UUID

interface AulaApiService {
    @GET("aula/all")
    suspend fun obtenerAulas(): Response<List<Aula>>

    @GET("aula/{id}")
    suspend fun obtenerAulaPorId(@Path("id") id: UUID): Response<Aula>

    @POST("aula/save")
    suspend fun guardarAula(@Body aula: Aula): Response<Aula>

    @PUT("aula/update")
    suspend fun actualizarAula(@Body aula: Aula): Response<Aula>

    @GET("aula/edificio/{id}")
    suspend fun obtenerAulasPorEdificioId(@Path("id") id: UUID): Response<List<Aula>>
}