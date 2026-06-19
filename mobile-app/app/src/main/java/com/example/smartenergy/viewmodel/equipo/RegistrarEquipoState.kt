package com.example.smartenergy.viewmodel.equipo

import com.example.smartenergy.model.Alerta
import com.example.smartenergy.model.Equipo


interface RegistrarEquipoState {
    data object Loading : RegistrarEquipoState

    data class Success(val equipo: Equipo): RegistrarEquipoState

    data class Error(val message: String): RegistrarEquipoState
}