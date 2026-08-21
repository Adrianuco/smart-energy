package com.example.smartenergy.viewmodel.incidencias

import com.example.smartenergy.model.Aula
import com.example.smartenergy.model.Incidencia

interface CrearIncidenciaState {
    data object Idle : CrearIncidenciaState

    data object Loading : CrearIncidenciaState

    data class Success(val incidencia: Incidencia): CrearIncidenciaState

    data class Error(val message: String): CrearIncidenciaState

    data class AulasSucces(val aulas: List<Aula>) : CrearIncidenciaState

    data class AulasError(val message: String): CrearIncidenciaState
}