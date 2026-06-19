package com.example.smartenergy.viewmodel.edificio

import com.example.smartenergy.model.Alerta
import com.example.smartenergy.model.Edificio
import com.example.smartenergy.viewmodel.alertas.AlertasState

interface EdificiosState {
    data object Loading : EdificiosState

    data class Success(val edificios: List<Edificio>): EdificiosState

    data class Error(val message: String): EdificiosState
}