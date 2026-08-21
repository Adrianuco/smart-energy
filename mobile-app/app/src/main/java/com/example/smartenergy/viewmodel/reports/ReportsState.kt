package com.example.smartenergy.viewmodel.reports

import com.example.smartenergy.model.Alerta
import com.example.smartenergy.model.Edificio

interface ReportsState {
    data object Loading : ReportsState

    data class Success(
        val edificios: List<Edificio>
    ): ReportsState

    data class Error(val message: String): ReportsState
}