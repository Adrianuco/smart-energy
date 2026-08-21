package com.example.smartenergy.repository

import com.example.smartenergy.model.Administrador
import com.example.smartenergy.service.AdministradorApiService
import com.example.smartenergy.service.ApiResult
import java.util.UUID

class AdministradorRepository(private val apiService: AdministradorApiService) {
    suspend fun findAll(): ApiResult<List<Administrador>> {
        return try {
            val response = apiService.obtenerAdministradors()
            if (response.isSuccessful) {
                ApiResult.Success(response.body() ?: emptyList())
            } else {
                ApiResult.Error("Error HTTP: ${response.code()}")
            }
        } catch (ex: Exception) {
            ApiResult.Error("Error: ${ex.message}")
        }
    }

    suspend fun findById(id: UUID): ApiResult<Administrador> {
        return try {
            val response = apiService.obtenerAdministradorPorId(id)
            if (response.isSuccessful) {
                ApiResult.Success(response.body()!!)
            } else {
                ApiResult.Error("Error HTTP: ${response.code()}")
            }
        } catch (ex: Exception) {
            ApiResult.Error("Error: ${ex.message}")
        }
    }
    suspend fun save(administrador: Administrador): ApiResult<Administrador> {
        return try {
            val response = apiService.guardarAdministrador(administrador)
            if (response.isSuccessful) {
                ApiResult.Success(response.body()!!)
            } else {
                ApiResult.Error("Error HTTP: ${response.code()}")
            }
        } catch (ex: Exception) {
            ApiResult.Error("Error: ${ex.message}")
        }
    }

    suspend fun update(administrador: Administrador): ApiResult<Administrador> {
        return try {
            val response = apiService.actualizarAdministrador(administrador)
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