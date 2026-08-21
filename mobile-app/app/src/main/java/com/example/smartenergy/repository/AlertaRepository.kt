package com.example.smartenergy.repository

import com.example.smartenergy.model.Alerta
import com.example.smartenergy.service.AlertaApiService
import com.example.smartenergy.service.ApiResult
import java.util.UUID

class AlertaRepository(private val apiService: AlertaApiService) {

    suspend fun findAll(): ApiResult<List<Alerta>> {
        return try {
            val response = apiService.obtenerAlertas()
            if (response.isSuccessful) {
                ApiResult.Success(response.body() ?: emptyList())
            } else {
                ApiResult.Error("Error HTTP: ${response.code()}")
            }
        } catch (ex: Exception) {
            ApiResult.Error("Error: ${ex.message}")
        }
    }

    suspend fun findById(id: UUID): ApiResult<Alerta> {
        return try {
            val response = apiService.obtenerAlertaPorId(id)
            if (response.isSuccessful) {
                ApiResult.Success(response.body()!!)
            } else {
                ApiResult.Error("Error HTTP: ${response.code()}")
            }
        } catch (ex: Exception) {
            ApiResult.Error("Error: ${ex.message}")
        }
    }
    suspend fun save(alerta: Alerta): ApiResult<Alerta> {
        return try {
            val response = apiService.guardarAlerta(alerta)
            if (response.isSuccessful) {
                ApiResult.Success(response.body()!!)
            } else {
                ApiResult.Error("Error HTTP: ${response.code()}")
            }
        } catch (ex: Exception) {
            ApiResult.Error("Error: ${ex.message}")
        }
    }

    suspend fun update(alerta: Alerta): ApiResult<Alerta> {
        return try {
            val response = apiService.actualizarAlerta(alerta)
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