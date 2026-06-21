package com.example.smartenergy.viewmodel.dashboard

import com.example.smartenergy.model.Alerta
import com.example.smartenergy.model.Dashboard

interface DashboardState {
    data object Loading : DashboardState

    data class Success(
        val dashboard: Dashboard
    ): DashboardState

    data class Error(val message: String): DashboardState
}