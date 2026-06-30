package com.example.smartenergy.viewmodel.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartenergy.model.ConfigSistema
import com.example.smartenergy.repository.ConfigSistemaRepository
import com.example.smartenergy.service.ApiResult
import com.example.smartenergy.viewmodel.OperationState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val repository: ConfigSistemaRepository
): ViewModel() {

    private val _state = MutableStateFlow<SettingsState>(SettingsState.Loading)
    val state = _state.asStateFlow()

    init {
        getConfig()
    }

    fun getConfig() {
        viewModelScope.launch {
            _state.value = SettingsState.Loading
            when(val result = repository.findAll()) {
                is ApiResult.Success -> {
                    val config = result.data.firstOrNull() ?: ConfigSistema(
                        margenEncendido = 15,
                        tiempoMinimoDesperdicio = 10,
                        activo = true
                    )
                    _state.value = SettingsState.Success(config)
                }
                is ApiResult.Error -> _state.value = SettingsState.Error(result.message)
            }
        }
    }

    private val _updateState = MutableStateFlow<OperationState>(OperationState.Idle)
    val updateState = _updateState.asStateFlow()

    fun resetUpdateState() {
        _updateState.value = OperationState.Idle
    }

    fun updateConfig(config: ConfigSistema) {
        viewModelScope.launch {
            _updateState.value = OperationState.Loading
            val result = repository.update(config)
            when (result) {
                is ApiResult.Success -> {
                    _state.value = SettingsState.Success(result.data)
                    _updateState.value = OperationState.Success
                }
                is ApiResult.Error -> {
                    _updateState.value = OperationState.Error(result.message)
                }
            }
        }
    }
}