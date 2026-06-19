package com.example.smartenergy.viewmodel.dashboard

import com.example.smartenergy.model.Alerta

interface DashboardState {
    data object Loading : DashboardState

    data class Success(
        val consumo: Double,
        val ahorro: Double,
        val numAlertas: Int,
        val numEquipos: Int
    ): DashboardState

    data class Error(val message: String): DashboardState
}