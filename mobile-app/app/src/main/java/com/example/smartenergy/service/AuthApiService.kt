package com.example.smartenergy.service

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

data class LoginRequest(
    val cif: String,
    val password: String
)

data class LoginResponse(
    val id: String,
    val nombre: String,
    val apellido: String,
    val rol: String
)

interface AuthApiService {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
}
