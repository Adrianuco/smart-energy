package com.example.smartenergy.repository

import com.example.smartenergy.model.HorarioAcademico
import com.example.smartenergy.service.ApiResult
import com.example.smartenergy.service.EquipoApiService
import com.example.smartenergy.service.HorarioAcademicoApiService
import okhttp3.MultipartBody
import java.util.UUID

class HorarioAcademicoRepository(private val apiService: HorarioAcademicoApiService) {

    suspend fun findAll(): ApiResult<List<HorarioAcademico>> {
        return try {
            val response = apiService.obtenerHorarioAcademicos()
            if (response.isSuccessful) {
                ApiResult.Success(response.body() ?: emptyList())
            } else {
                ApiResult.Error("Error HTTP: ${response.code()}")
            }
        } catch (ex: Exception) {
            ApiResult.Error("Error: ${ex.message}")
        }
    }

    suspend fun findById(id: UUID): ApiResult<HorarioAcademico> {
        return try {
            val response = apiService.obtenerHorarioAcademicoPorId(id)
            if (response.isSuccessful) {
                ApiResult.Success(response.body()!!)
            } else {
                ApiResult.Error("Error HTTP: ${response.code()}")
            }
        } catch (ex: Exception) {
            ApiResult.Error("Error: ${ex.message}")
        }
    }
    suspend fun save(horarioAcademico: HorarioAcademico): ApiResult<HorarioAcademico> {
        return try {
            val response = apiService.guardarHorarioAcademico(horarioAcademico)
            if (response.isSuccessful) {
                ApiResult.Success(response.body()!!)
            } else {
                ApiResult.Error("Error HTTP: ${response.code()}")
            }
        } catch (ex: Exception) {
            ApiResult.Error("Error: ${ex.message}")
        }
    }

    suspend fun update(horarioAcademico: HorarioAcademico): ApiResult<HorarioAcademico> {
        return try {
            val response = apiService.actualizarHorarioAcademico(horarioAcademico)
            if (response.isSuccessful) {
                ApiResult.Success(response.body()!!)
            } else {
                ApiResult.Error("Error HTTP: ${response.code()}")
            }
        } catch (ex: Exception) {
            ApiResult.Error("Error: ${ex.message}")
        }
    }

    suspend fun import(file: MultipartBody.Part): ApiResult<String> {
        return try {
            val response = apiService.importarHorarios(file)
            if (response.isSuccessful) {
                ApiResult.Success(response.body()!!)
            } else {
                ApiResult.Error("Error HTTP: ${response.code()}")
            }
        } catch (ex: Exception) {
            ApiResult.Error("Error: ${ex.message}")
        }
    }
}