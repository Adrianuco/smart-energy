package com.example.smartenergy.service

import com.example.smartenergy.model.AsignacionEquiposRequest
import com.example.smartenergy.model.Equipo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import java.util.UUID

interface EquipoApiService {
    @GET("equipo/all")
    suspend fun obtenerEquipos(): Response<List<Equipo>>

    @GET("equipo/{id}")
    suspend fun obtenerEquipoPorId(@Path("id") id: UUID): Response<Equipo>

    @POST("equipo/save")
    suspend fun guardarEquipo(@Body equipo: Equipo): Response<Equipo>

    @PUT("equipo/update")
    suspend fun actualizarEquipo(@Body equipo: Equipo): Response<Equipo>

    @POST("equipo/asignar-equipos")
    suspend fun asignarEquipos(@Body request: AsignacionEquiposRequest): Response<Void>
}