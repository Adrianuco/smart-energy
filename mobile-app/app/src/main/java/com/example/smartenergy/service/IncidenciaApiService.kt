package com.example.smartenergy.service

import com.example.smartenergy.model.Incidencia
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import java.util.UUID

interface IncidenciaApiService {
    @GET("incidencia/all")
    suspend fun obtenerIncidencias(): Response<List<Incidencia>>

    @GET("incidencia/{id}")
    suspend fun obtenerIncidenciaPorId(@Path("id") id: UUID): Response<Incidencia>

    @POST("incidencia/save")
    suspend fun guardarIncidencia(@Body incidencia: Incidencia): Response<Incidencia>

    @PUT("incidencia/update")
    suspend fun actualizarIncidencia(@Body incidencia: Incidencia): Response<Incidencia>
}