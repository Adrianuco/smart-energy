package com.example.smartenergy.viewmodel.alertas

import com.example.smartenergy.model.Alerta


interface AtenderAlertaState {
    data object Loading : AtenderAlertaState

    data class Success(val alerta: Alerta): AtenderAlertaState

    data class Error(val message: String): AtenderAlertaState
}