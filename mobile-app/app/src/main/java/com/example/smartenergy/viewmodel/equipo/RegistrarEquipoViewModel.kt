package com.example.smartenergy.viewmodel.equipo

import androidx.lifecycle.ViewModel
import com.example.smartenergy.repository.EquipoRepository

import androidx.lifecycle.viewModelScope
import com.example.smartenergy.model.Equipo
import com.example.smartenergy.service.ApiResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegistrarEquipoViewModel(
    private val repository: EquipoRepository
): ViewModel() {

    private val _state = MutableStateFlow<RegistrarEquipoState?>(null)
    val state = _state.asStateFlow()

    // metodo para registrar un equipo
    fun registrar(equipo: Equipo) {
        viewModelScope.launch {
            _state.value = RegistrarEquipoState.Loading
            when (val result = repository.save(equipo)) {
                is ApiResult.Success -> {
                    _state.value = RegistrarEquipoState.Success(result.data)
                }
                is ApiResult.Error -> {
                    _state.value = RegistrarEquipoState.Error(result.message)
                }
            }
        }
    }

    fun resetState() {
        _state.value = null
    }
}