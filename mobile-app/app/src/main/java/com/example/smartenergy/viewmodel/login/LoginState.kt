package com.example.smartenergy.viewmodel.login

import com.example.smartenergy.model.Alerta

interface LoginState {
    data object Loading : LoginState

    data class Success(val inicio: Boolean): LoginState

    data class Error(val message: String): LoginState
}