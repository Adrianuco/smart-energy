package com.example.smartenergy.viewmodel.incidencias

import com.example.smartenergy.model.Alerta
import com.example.smartenergy.model.Incidencia


interface IncidenciasState {
    data object Loading : IncidenciasState

    data class Success(val incidencias: List<Incidencia>): IncidenciasState

    data class Error(val message: String): IncidenciasState
}