package com.example.smartenergy.repository

import com.example.smartenergy.model.ConfigSistema
import com.example.smartenergy.model.Dashboard
import com.example.smartenergy.service.ApiResult
import com.example.smartenergy.service.DashboardApiService

class DashboardRepository(
    private val apiService: DashboardApiService
) {
    suspend fun getDashboard(): ApiResult<Dashboard> {
        return try {
            val response = apiService.dashboard()
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