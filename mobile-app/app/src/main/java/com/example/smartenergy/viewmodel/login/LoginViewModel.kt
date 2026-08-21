package com.example.smartenergy.viewmodel.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartenergy.repository.AuthRepository
import com.example.smartenergy.service.ApiResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: AuthRepository
): ViewModel() {

    private val _state = MutableStateFlow<LoginState>(LoginState.Success(inicio = false))
    val state = _state.asStateFlow()

    fun iniciarSesion(cif: String, password: String) {
        viewModelScope.launch {
            _state.value = LoginState.Loading
            when (val result = repository.login(cif, password)) {
                is ApiResult.Success -> {
                    _state.value = LoginState.Success(inicio = true)
                }
                is ApiResult.Error -> {
                    _state.value = LoginState.Error(result.message)
                }
            }
        }
    }
}