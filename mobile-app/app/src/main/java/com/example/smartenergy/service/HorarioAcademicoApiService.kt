package com.example.smartenergy.service

import com.example.smartenergy.model.HorarioAcademico
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import java.util.UUID

interface HorarioAcademicoApiService {
    @GET("horario/all")
    suspend fun obtenerHorarioAcademicos(): Response<List<HorarioAcademico>>

    @GET("horario/{id}")
    suspend fun obtenerHorarioAcademicoPorId(@Path("id") id: UUID): Response<HorarioAcademico>

    @POST("horario/save")
    suspend fun guardarHorarioAcademico(@Body horarioAcademico: HorarioAcademico): Response<HorarioAcademico>

    @PUT("horario/update")
    suspend fun actualizarHorarioAcademico(@Body horarioAcademico: HorarioAcademico): Response<HorarioAcademico>

    @Multipart
    @POST("horario/upload")
    suspend fun importarHorarios(@Part file: MultipartBody.Part): Response<String>
}