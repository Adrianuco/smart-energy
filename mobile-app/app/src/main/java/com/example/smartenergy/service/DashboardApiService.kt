package com.example.smartenergy.service

import com.example.smartenergy.model.Dashboard
import retrofit2.Response
import retrofit2.http.GET

interface DashboardApiService {

    @GET("dashboard/get")
    suspend fun dashboard(): Response<Dashboard>
}