package com.example.smartenergy.viewmodel.edificio

import com.example.smartenergy.model.Alerta
import com.example.smartenergy.model.Edificio

interface DetalleEdificioState {
    data object Loading : DetalleEdificioState

    data class Success(val edificio: Edificio): DetalleEdificioState

    data class Error(val message: String): DetalleEdificioState
}