package com.example.smartenergy.repository

import com.example.smartenergy.model.AsignacionEdificio
import com.example.smartenergy.service.ApiResult
import com.example.smartenergy.service.AsignacionEdificoApiService
import java.util.UUID

class AsignacionEdificioRepository(private val apiService: AsignacionEdificoApiService) {

    suspend fun findAll(): ApiResult<List<AsignacionEdificio>> {
        return try {
            val response = apiService.obtenerAsignacionEdificios()
            if (response.isSuccessful) {
                ApiResult.Success(response.body() ?: emptyList())
            } else {
                ApiResult.Error("Error HTTP: ${response.code()}")
            }
        } catch (ex: Exception) {
            ApiResult.Error("Error: ${ex.message}")
        }
    }

    suspend fun findById(id: UUID): ApiResult<AsignacionEdificio> {
        return try {
            val response = apiService.obtenerAsignacionEdificioPorId(id)
            if (response.isSuccessful) {
                ApiResult.Success(response.body()!!)
            } else {
                ApiResult.Error("Error HTTP: ${response.code()}")
            }
        } catch (ex: Exception) {
            ApiResult.Error("Error: ${ex.message}")
        }
    }
    suspend fun save(asignacionEdificio: AsignacionEdificio): ApiResult<AsignacionEdificio> {
        return try {
            val response = apiService.guardarAsignacionEdificio(asignacionEdificio)
            if (response.isSuccessful) {
                ApiResult.Success(response.body()!!)
            } else {
                ApiResult.Error("Error HTTP: ${response.code()}")
            }
        } catch (ex: Exception) {
            ApiResult.Error("Error: ${ex.message}")
        }
    }

    suspend fun update(asignacionEdificio: AsignacionEdificio): ApiResult<AsignacionEdificio> {
        return try {
            val response = apiService.actualizarAsignacionEdificio(asignacionEdificio)
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