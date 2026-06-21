package com.example.smartenergy.viewmodel.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartenergy.model.ConfigSistema
import com.example.smartenergy.repository.ConfigSistemaRepository
import com.example.smartenergy.service.ApiResult
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

    fun updateConfig(config: ConfigSistema, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val result = repository.update(config)
            when (result) {
                is ApiResult.Success -> {
                    _state.value = SettingsState.Success(result.data)
                    onResult(true)
                }
                is ApiResult.Error -> {
                    onResult(false)
                }
            }
        }
    }
}