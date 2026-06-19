package com.example.smartenergy.service

import com.example.smartenergy.model.RegistroOperativo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import java.util.UUID

interface RegistroOperativoApiService {
    @GET("registro/all")
    suspend fun obtenerRegistroOperativos(): Response<List<RegistroOperativo>>

    @GET("registro/{id}")
    suspend fun obtenerRegistroOperativoPorId(@Path("id") id: UUID): Response<RegistroOperativo>

    @POST("registro/save")
    suspend fun guardarRegistroOperativo(@Body registroOperativo: RegistroOperativo): Response<RegistroOperativo>

    @PUT("registro/update")
    suspend fun actualizarRegistroOperativo(@Body registroOperativo: RegistroOperativo): Response<RegistroOperativo>
}