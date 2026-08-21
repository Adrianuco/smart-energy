package com.example.smartenergy.repository

import com.example.smartenergy.model.Incidencia
import com.example.smartenergy.service.ApiResult
import com.example.smartenergy.service.IncidenciaApiService
import java.util.UUID

class IncidenciaRepository(private val apiService: IncidenciaApiService) {

    suspend fun findAll(): ApiResult<List<Incidencia>> {
        return try {
            val response = apiService.obtenerIncidencias()
            if (response.isSuccessful) {
                ApiResult.Success(response.body() ?: emptyList())
            } else {
                ApiResult.Error("Error HTTP: ${response.code()}")
            }
        } catch (ex: Exception) {
            ApiResult.Error("Error: ${ex.message}")
        }
    }

    suspend fun findById(id: UUID): ApiResult<Incidencia> {
        return try {
            val response = apiService.obtenerIncidenciaPorId(id)
            if (response.isSuccessful) {
                ApiResult.Success(response.body()!!)
            } else {
                ApiResult.Error("Error HTTP: ${response.code()}")
            }
        } catch (ex: Exception) {
            ApiResult.Error("Error: ${ex.message}")
        }
    }
    suspend fun save(incidencia: Incidencia): ApiResult<Incidencia> {
        return try {
            val response = apiService.guardarIncidencia(incidencia)
            if (response.isSuccessful) {
                ApiResult.Success(response.body()!!)
            } else {
                ApiResult.Error("Error HTTP: ${response.code()}")
            }
        } catch (ex: Exception) {
            ApiResult.Error("Error: ${ex.message}")
        }
    }

    suspend fun update(incidencia: Incidencia): ApiResult<Incidencia> {
        return try {
            val response = apiService.actualizarIncidencia(incidencia)
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
