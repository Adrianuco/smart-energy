package com.example.smartenergy.viewmodel.equipo

import com.example.smartenergy.model.Alerta
import com.example.smartenergy.model.Equipo

interface EquiposState {
    data object Loading : EquiposState

    data class Success(val equipos: List<Equipo>): EquiposState

    data class Error(val message: String): EquiposState
}
