package com.example.smartenergy.viewmodel.incidencias

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartenergy.model.Aula
import com.example.smartenergy.model.Incidencia
import com.example.smartenergy.repository.AulaRepository
import com.example.smartenergy.repository.IncidenciaRepository
import com.example.smartenergy.service.ApiResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CrearIncidenciaViewModel(
    private val repository: IncidenciaRepository,
    private val aulaRepository: AulaRepository
): ViewModel() {
    private val _state = MutableStateFlow<AtenderIncidenciaState>(AtenderIncidenciaState.Idle)
    val state = _state.asStateFlow()

    private val _aulas = MutableStateFlow<List<Aula>>(emptyList())
    val aulas = _aulas.asStateFlow()

    init {
        loadAulas()
    }

    private fun loadAulas() {
        viewModelScope.launch {
            when (val result = aulaRepository.findAll()) {
                is ApiResult.Success -> _aulas.value = result.data
                else -> { /* ignore error */ }
            }
        }
    }

    fun save(incidencia: Incidencia) {
        viewModelScope.launch{
            _state.value = AtenderIncidenciaState.Loading
            when(val result = repository.save(incidencia)) {
                is ApiResult.Success -> _state.value = AtenderIncidenciaState.Success(result.data)
                is ApiResult.Error -> _state.value = AtenderIncidenciaState.Error(result.message)
            }
        }
    }
}