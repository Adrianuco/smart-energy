package com.example.smartenergy.service

import com.example.smartenergy.model.AsignacionEdificio
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import java.util.UUID

interface AsignacionEdificoApiService {
    @GET("asignacion/all")
    suspend fun obtenerAsignacionEdificios(): Response<List<AsignacionEdificio>>

    @GET("asignacion/{id}")
    suspend fun obtenerAsignacionEdificioPorId(@Path("id") id: UUID): Response<AsignacionEdificio>

    @POST("asignacion/save")
    suspend fun guardarAsignacionEdificio(@Body asignacionEdificio: AsignacionEdificio): Response<AsignacionEdificio>

    @PUT("asignacion/update")
    suspend fun actualizarAsignacionEdificio(@Body asignacionEdificio: AsignacionEdificio): Response<AsignacionEdificio>
}