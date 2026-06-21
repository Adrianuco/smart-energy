package com.example.smartenergy.viewmodel.incidencias

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartenergy.model.Incidencia
import com.example.smartenergy.repository.IncidenciaRepository
import com.example.smartenergy.service.ApiResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AtenderIncidenciaViewModel(
    private val repository: IncidenciaRepository
): ViewModel() {
    private val _state = MutableStateFlow<AtenderIncidenciaState>(AtenderIncidenciaState.Loading)

    val state = _state.asStateFlow()


    fun update(incidencia: Incidencia) {
        viewModelScope.launch{
            _state.value = AtenderIncidenciaState.Loading
            when(val result = repository.update(incidencia)) {
                is ApiResult.Success -> _state.value = AtenderIncidenciaState.Success(result.data)
                is ApiResult.Error -> _state.value = AtenderIncidenciaState.Error(result.message)
            }
        }
    }
}