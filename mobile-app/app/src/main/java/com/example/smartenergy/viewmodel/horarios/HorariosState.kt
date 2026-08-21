package com.example.smartenergy.viewmodel.horarios

import com.example.smartenergy.model.Aula
import com.example.smartenergy.model.Equipo
import com.example.smartenergy.model.HorarioAcademico


interface HorariosState {
    data object Loading : HorariosState

    data class Success(
        val horarios: List<HorarioAcademico>,
        val aulasSinEquipo: List<Aula>,
        val equiposDisponibles: List<Equipo>,
        val todasAulas: List<Aula> = emptyList()
    ): HorariosState

    data class Error(val message: String): HorariosState
}