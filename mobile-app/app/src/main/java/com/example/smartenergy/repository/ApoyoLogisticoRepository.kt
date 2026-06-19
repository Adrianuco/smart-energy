package com.example.smartenergy.repository

import com.example.smartenergy.model.ApoyoLogistico
import com.example.smartenergy.service.ApiResult
import com.example.smartenergy.service.ApoyoLogisticaApiService
import java.util.UUID

class ApoyoLogisticoRepository(private val apiService: ApoyoLogisticaApiService) {
    suspend fun findAll(): ApiResult<List<ApoyoLogistico>> {
        return try {
            val response = apiService.obtenerApoyoLogisticos()
            if (response.isSuccessful) {
                ApiResult.Success(response.body() ?: emptyList())
            } else {
                ApiResult.Error("Error HTTP: ${response.code()}")
            }
        } catch (ex: Exception) {
            ApiResult.Error("Error: ${ex.message}")
        }
    }

    suspend fun findById(id: UUID): ApiResult<ApoyoLogistico> {
        return try {
            val response = apiService.obtenerApoyoLogisticoPorId(id)
            if (response.isSuccessful) {
                ApiResult.Success(response.body()!!)
            } else {
                ApiResult.Error("Error HTTP: ${response.code()}")
            }
        } catch (ex: Exception) {
            ApiResult.Error("Error: ${ex.message}")
        }
    }
    suspend fun save(apoyoLogistico: ApoyoLogistico): ApiResult<ApoyoLogistico> {
        return try {
            val response = apiService.guardarApoyoLogistico(apoyoLogistico)
            if (response.isSuccessful) {
                ApiResult.Success(response.body()!!)
            } else {
                ApiResult.Error("Error HTTP: ${response.code()}")
            }
        } catch (ex: Exception) {
            ApiResult.Error("Error: ${ex.message}")
        }
    }

    suspend fun update(apoyoLogistico: ApoyoLogistico): ApiResult<ApoyoLogistico> {
        return try {
            val response = apiService.actualizarApoyoLogistico(apoyoLogistico)
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