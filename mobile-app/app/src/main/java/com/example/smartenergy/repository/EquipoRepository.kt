package com.example.smartenergy.repository

import com.example.smartenergy.model.AsignacionEquiposRequest
import com.example.smartenergy.model.Equipo
import com.example.smartenergy.service.ApiResult
import com.example.smartenergy.service.EquipoApiService
import java.util.UUID

class EquipoRepository(private val apiService: EquipoApiService) {

    suspend fun findAll(): ApiResult<List<Equipo>> {
        return try {
            val response = apiService.obtenerEquipos()
            if (response.isSuccessful) {
                ApiResult.Success(response.body() ?: emptyList())
            } else {
                ApiResult.Error("Error HTTP: ${response.code()}")
            }
        } catch (ex: Exception) {
            ApiResult.Error("Error: ${ex.message}")
        }
    }

    suspend fun findById(id: UUID): ApiResult<Equipo> {
        return try {
            val response = apiService.obtenerEquipoPorId(id)
            if (response.isSuccessful) {
                ApiResult.Success(response.body()!!)
            } else {
                ApiResult.Error("Error HTTP: ${response.code()}")
            }
        } catch (ex: Exception) {
            ApiResult.Error("Error: ${ex.message}")
        }
    }
    suspend fun save(equipo: Equipo): ApiResult<Equipo> {
        return try {
            val response = apiService.guardarEquipo(equipo)
            if (response.isSuccessful) {
                ApiResult.Success(response.body()!!)
            } else {
                ApiResult.Error("Error HTTP: ${response.code()}")
            }
        } catch (ex: Exception) {
            ApiResult.Error("Error: ${ex.message}")
        }
    }

    suspend fun update(equipo: Equipo): ApiResult<Equipo> {
        return try {
            val response = apiService.actualizarEquipo(equipo)
            if (response.isSuccessful) {
                ApiResult.Success(response.body()!!)
            } else {
                ApiResult.Error("Error HTTP: ${response.code()}")
            }
        } catch (ex: Exception) {
            ApiResult.Error("Error: ${ex.message}")
        }
    }

    suspend fun asignarEquipos(request: AsignacionEquiposRequest): ApiResult<Unit> {
        return try {
            val response = apiService.asignarEquipos(request)
            if (response.isSuccessful) {
                ApiResult.Success(Unit)
            } else {
                ApiResult.Error("Error HTTP: ${response.code()}")
            }
        } catch (ex: Exception) {
            ApiResult.Error("Error: ${ex.message}")
        }
    }
}