package com.example.smartenergy.repository

import com.example.smartenergy.model.Aula
import com.example.smartenergy.service.ApiResult
import com.example.smartenergy.service.AulaApiService
import java.util.UUID

class AulaRepository(private val apiService: AulaApiService) {

    suspend fun findAll(): ApiResult<List<Aula>> {
        return try {
            val response = apiService.obtenerAulas()
            if (response.isSuccessful) {
                ApiResult.Success(response.body() ?: emptyList())
            } else {
                ApiResult.Error("Error HTTP: ${response.code()}")
            }
        } catch (ex: Exception) {
            ApiResult.Error("Error: ${ex.message}")
        }
    }

    suspend fun findById(id: UUID): ApiResult<Aula> {
        return try {
            val response = apiService.obtenerAulaPorId(id)
            if (response.isSuccessful) {
                ApiResult.Success(response.body()!!)
            } else {
                ApiResult.Error("Error HTTP: ${response.code()}")
            }
        } catch (ex: Exception) {
            ApiResult.Error("Error: ${ex.message}")
        }
    }
    suspend fun save(aula: Aula): ApiResult<Aula> {
        return try {
            val response = apiService.guardarAula(aula)
            if (response.isSuccessful) {
                ApiResult.Success(response.body()!!)
            } else {
                ApiResult.Error("Error HTTP: ${response.code()}")
            }
        } catch (ex: Exception) {
            ApiResult.Error("Error: ${ex.message}")
        }
    }

    suspend fun update(aula: Aula): ApiResult<Aula> {
        return try {
            val response = apiService.actualizarAula(aula)
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
