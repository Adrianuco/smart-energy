package com.example.smartenergy.viewmodel.alertas

import android.os.Message
import com.example.smartenergy.model.Alerta

interface AlertasState {

    data object Loading : AlertasState

    data class Success(val alertas: List<Alerta>): AlertasState

    data class Error(val message: String): AlertasState
}