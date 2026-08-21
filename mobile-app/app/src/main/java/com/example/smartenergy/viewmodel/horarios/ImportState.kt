package com.example.smartenergy.viewmodel.horarios

interface ImportState {

    data object Idle : ImportState

    data object Loading : ImportState

    data class Success(
        val message: String
    ): ImportState

    data class Error(
        val message: String
    ): ImportState
}