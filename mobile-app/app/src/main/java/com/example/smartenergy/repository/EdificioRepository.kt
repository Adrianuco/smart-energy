package com.example.smartenergy.repository

import com.example.smartenergy.model.DetalleEdificio
import com.example.smartenergy.model.Edificio
import com.example.smartenergy.service.ApiResult
import com.example.smartenergy.service.EdificioApiService
import java.util.UUID

class EdificioRepository(private val apiService: EdificioApiService) {

    suspend fun findAll(): ApiResult<List<Edificio>> {
        return try {
            val response = apiService.obtenerEdificios()
            if (response.isSuccessful) {
                ApiResult.Success(response.body() ?: emptyList())
            } else {
                ApiResult.Error("Error HTTP: ${response.code()}")
            }
        } catch (ex: Exception) {
            ApiResult.Error("Error: ${ex.message}")
        }
    }

    suspend fun findDetalle(id: UUID): ApiResult<DetalleEdificio> {
        return try {
            val response = apiService.obtenerDetalle(id)
            if (response.isSuccessful) {
                ApiResult.Success(response.body()!!)
            } else {
                ApiResult.Error("Error HTTP: ${response.code()}")
            }
        } catch (ex: Exception) {
            ApiResult.Error("Error: ${ex.message}")
        }
    }

    suspend fun save(edificio: Edificio): ApiResult<Edificio> {
        return try {
            val response = apiService.guardarEdificio(edificio)
            if (response.isSuccessful) {
                ApiResult.Success(response.body()!!)
            } else {
                ApiResult.Error("Error HTTP: ${response.code()}")
            }
        } catch (ex: Exception) {
            ApiResult.Error("Error: ${ex.message}")
        }
    }

    suspend fun update(edificio: Edificio): ApiResult<Edificio> {
        return try {
            val response = apiService.actualizarEdificio(edificio)
            if (response.isSuccessful) {
                ApiResult.Success(response.body()!!)
            } else {
                ApiResult.Error("Error HTTP: ${response.code()}")
            }
        } catch (ex: Exception) {
            ApiResult.Error("Error: ${ex.message}")
        }
    }

    suspend fun getConsumoHistorico(id: UUID, periodo: String): ApiResult<List<Double>> {
        return try {
            val response = apiService.obtenerConsumoHistorico(id, periodo)
            if (response.isSuccessful) {
                ApiResult.Success(response.body() ?: emptyList())
            } else {
                ApiResult.Error("Error HTTP: ${response.code()}")
            }
        } catch (ex: Exception) {
            ApiResult.Error("Error: ${ex.message}")
        }
    }
}
