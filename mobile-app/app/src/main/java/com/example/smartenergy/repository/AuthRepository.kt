package com.example.smartenergy.repository

import com.example.smartenergy.service.ApiResult
import com.example.smartenergy.service.AuthApiService
import com.example.smartenergy.service.LoginRequest
import com.example.smartenergy.service.LoginResponse

class AuthRepository(private val apiService: AuthApiService) {
    suspend fun login(cif: String, password: String): ApiResult<LoginResponse> {
        return try {
            val response = apiService.login(LoginRequest(cif, password))
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
