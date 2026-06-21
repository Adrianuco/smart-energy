package com.example.smartenergy.viewmodel.incidencias

import com.example.smartenergy.model.Incidencia

interface CrearIncidenciaState {
    data object Loading : AtenderIncidenciaState

    data class Success(val incidencia: Incidencia): AtenderIncidenciaState

    data class Error(val message: String): AtenderIncidenciaState
}