package com.example.smartenergy.viewmodel.infrastructure

import com.example.smartenergy.model.Alerta
import com.example.smartenergy.model.Aula
import com.example.smartenergy.model.Edificio
import com.example.smartenergy.model.Equipo

interface InfrastructureState {
    data object Loading : InfrastructureState

    data class Success(
        val equipos: List<Equipo>,
        val edificios: List<Edificio>,
        val aulas: List<Aula>
    ): InfrastructureState

    data class Error(val message: String): InfrastructureState
}