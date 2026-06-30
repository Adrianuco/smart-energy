package com.example.smartenergy.viewmodel.infrastructure

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartenergy.model.Aula
import com.example.smartenergy.model.Edificio
import com.example.smartenergy.repository.AulaRepository
import com.example.smartenergy.repository.EdificioRepository
import com.example.smartenergy.repository.EquipoRepository
import com.example.smartenergy.service.ApiResult
import com.example.smartenergy.viewmodel.OperationState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class InfrastructureViewModel(
    private val equipoRepository: EquipoRepository,
    private val edificioRepository: EdificioRepository,
    private val aulaRepository: AulaRepository
): ViewModel() {

    private val _state = MutableStateFlow<InfrastructureState>(InfrastructureState.Loading)
    val state = _state.asStateFlow()

    init {
        loadInfrastructure()
    }

    fun loadInfrastructure() {
        viewModelScope.launch {
            _state.value = InfrastructureState.Loading
            val equiposResult = equipoRepository.findAll()
            val edificiosResult = edificioRepository.findAll()
            val aulasResult = aulaRepository.findAll()

            if (equiposResult is ApiResult.Success &&
                edificiosResult is ApiResult.Success &&
                aulasResult is ApiResult.Success) {
                _state.value = InfrastructureState.Success(
                    equipos = equiposResult.data,
                    edificios = edificiosResult.data,
                    aulas = aulasResult.data
                )
            } else {
                val eqMsg = (equiposResult as? ApiResult.Error)?.message ?: ""
                val edMsg = (edificiosResult as? ApiResult.Error)?.message ?: ""
                val auMsg = (aulasResult as? ApiResult.Error)?.message ?: ""
                _state.value = InfrastructureState.Error(
                    "Error al cargar infraestructura: Equipos: $eqMsg, Edificios: $edMsg, Aulas: $auMsg"
                )
            }
        }
    }

    private val _saveState = MutableStateFlow<OperationState>(OperationState.Idle)
    val saveState = _saveState.asStateFlow()

    fun resetSaveState() {
        _saveState.value = OperationState.Idle
    }

    fun guardarEdificio(edificio: Edificio) {
        viewModelScope.launch {
            _saveState.value = OperationState.Loading
            when (edificioRepository.save(edificio)) {
                is ApiResult.Success -> {
                    loadInfrastructure()
                    _saveState.value = OperationState.Success
                }
                is ApiResult.Error -> {
                    _saveState.value = OperationState.Error("Error")
                }
            }
        }
    }

    fun guardarAula(aula: Aula) {
        viewModelScope.launch {
            _saveState.value = OperationState.Loading
            when (aulaRepository.save(aula)) {
                is ApiResult.Success -> {
                    loadInfrastructure()
                    _saveState.value = OperationState.Success
                }
                is ApiResult.Error -> {
                    _saveState.value = OperationState.Error("Error")
                }
            }
        }
    }
}