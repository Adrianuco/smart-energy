package com.example.smartenergy.viewmodel.incidencias

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartenergy.model.Incidencia
import com.example.smartenergy.repository.IncidenciaRepository
import com.example.smartenergy.service.ApiResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class IncidenciasViewModel(
    private val repository: IncidenciaRepository
): ViewModel() {
    private val _state = MutableStateFlow<IncidenciasState>(IncidenciasState.Loading)

    val state = _state.asStateFlow()

    init {
        findAll()
    }

    private fun findAll() {
        viewModelScope.launch{
            _state.value = IncidenciasState.Loading
            when(val result = repository.findAll()) {
                is ApiResult.Success -> _state.value = IncidenciasState.Success(result.data)
                is ApiResult.Error -> _state.value = IncidenciasState.Error(result.message)
            }
        }
    }
}