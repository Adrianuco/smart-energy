package com.example.smartenergy.viewmodel.alertas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartenergy.model.Alerta
import com.example.smartenergy.repository.AlertaRepository
import com.example.smartenergy.service.ApiResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AlertasViewModel(
    private val repository: AlertaRepository
): ViewModel() {

    private val _state = MutableStateFlow<AlertasState>(AlertasState.Loading)

    val state = _state.asStateFlow()

    init {
        findAll()
    }

    private fun findAll() {
        viewModelScope.launch{
            _state.value = AlertasState.Loading
            when(val result = repository.findAll()) {
                is ApiResult.Success -> _state.value = AlertasState.Success(result.data)
                is ApiResult.Error -> _state.value = AlertasState.Error(result.message)
            }
        }
    }
}