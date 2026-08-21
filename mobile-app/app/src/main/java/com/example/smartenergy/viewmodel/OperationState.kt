package com.example.smartenergy.viewmodel

sealed class OperationState {
    data object Idle : OperationState()
    data object Loading : OperationState()
    data object Success : OperationState()
    data class Error(val message: String) : OperationState()
}
