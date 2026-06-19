package com.example.smartenergy.viewmodel.horarios

import com.example.smartenergy.model.Alerta
import com.example.smartenergy.model.HorarioAcademico


interface HorariosState {
    data object Loading : HorariosState

    data class Success(val horarios: List<HorarioAcademico>): HorariosState

    data class Error(val message: String): HorariosState
}