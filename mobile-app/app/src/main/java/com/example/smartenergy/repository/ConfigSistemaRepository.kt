package com.example.smartenergy.repository

import com.example.smartenergy.model.ConfigSistema
import com.example.smartenergy.service.ApiResult
import com.example.smartenergy.service.ConfigSistemaApiService
import java.util.UUID

class ConfigSistemaRepository(private val apiService: ConfigSistemaApiService) {

    suspend fun findAll(): ApiResult<List<ConfigSistema>> {
        return try {
            val response = apiService.obtenerConfigSistemas()
            if (response.isSuccessful) {
                ApiResult.Success(response.body() ?: emptyList())
            } else {
                ApiResult.Error("Error HTTP: ${response.code()}")
            }
        } catch (ex: Exception) {
            ApiResult.Error("Error: ${ex.message}")
        }
    }

    suspend fun findById(id: UUID): ApiResult<ConfigSistema> {
        return try {
            val response = apiService.obtenerConfigSistemaPorId(id)
            if (response.isSuccessful) {
                ApiResult.Success(response.body()!!)
            } else {
                ApiResult.Error("Error HTTP: ${response.code()}")
            }
        } catch (ex: Exception) {
            ApiResult.Error("Error: ${ex.message}")
        }
    }
    suspend fun save(configSistema: ConfigSistema): ApiResult<ConfigSistema> {
        return try {
            val response = apiService.guardarConfigSistema(configSistema)
            if (response.isSuccessful) {
                ApiResult.Success(response.body()!!)
            } else {
                ApiResult.Error("Error HTTP: ${response.code()}")
            }
        } catch (ex: Exception) {
            ApiResult.Error("Error: ${ex.message}")
        }
    }

    suspend fun update(configSistema: ConfigSistema): ApiResult<ConfigSistema> {
        return try {
            val response = apiService.actualizarConfigSistema(configSistema)
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