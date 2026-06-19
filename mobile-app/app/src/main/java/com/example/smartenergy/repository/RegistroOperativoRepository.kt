package com.example.smartenergy.repository

import com.example.smartenergy.model.RegistroOperativo
import com.example.smartenergy.service.ApiResult
import com.example.smartenergy.service.RegistroOperativoApiService
import java.util.UUID

class RegistroOperativoRepository(private val apiService: RegistroOperativoApiService) {

    suspend fun findAll(): ApiResult<List<RegistroOperativo>> {
        return try {
            val response = apiService.obtenerRegistroOperativos()
            if (response.isSuccessful) {
                ApiResult.Success(response.body() ?: emptyList())
            } else {
                ApiResult.Error("Error HTTP: ${response.code()}")
            }
        } catch (ex: Exception) {
            ApiResult.Error("Error: ${ex.message}")
        }
    }

    suspend fun findById(id: UUID): ApiResult<RegistroOperativo> {
        return try {
            val response = apiService.obtenerRegistroOperativoPorId(id)
            if (response.isSuccessful) {
                ApiResult.Success(response.body()!!)
            } else {
                ApiResult.Error("Error HTTP: ${response.code()}")
            }
        } catch (ex: Exception) {
            ApiResult.Error("Error: ${ex.message}")
        }
    }
    suspend fun save(registroOperativo: RegistroOperativo): ApiResult<RegistroOperativo> {
        return try {
            val response = apiService.guardarRegistroOperativo(registroOperativo)
            if (response.isSuccessful) {
                ApiResult.Success(response.body()!!)
            } else {
                ApiResult.Error("Error HTTP: ${response.code()}")
            }
        } catch (ex: Exception) {
            ApiResult.Error("Error: ${ex.message}")
        }
    }

    suspend fun update(registroOperativo: RegistroOperativo): ApiResult<RegistroOperativo> {
        return try {
            val response = apiService.actualizarRegistroOperativo(registroOperativo)
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

