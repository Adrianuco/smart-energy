package com.example.smartenergy.viewmodel.alertas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartenergy.repository.AlertaRepository
import com.example.smartenergy.repository.RegistroOperativoRepository
import com.example.smartenergy.service.ApiResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.example.smartenergy.model.Alerta
import com.example.smartenergy.model.Estado
import com.example.smartenergy.model.EstadoAlerta
import com.example.smartenergy.viewmodel.OperationState
import java.util.UUID

class AtenderAlertaViewModel(
    private val repository: AlertaRepository,
    private val registroOperativoRepository: RegistroOperativoRepository
): ViewModel() {

    private val _state = MutableStateFlow<AtenderAlertaState>(AtenderAlertaState.Loading)

    val state = _state.asStateFlow()

    // buscar alerta al seleccionarla
    fun findById(id: UUID) {
        viewModelScope.launch{
            when(val result = repository.findById(id)) {
                is ApiResult.Success -> _state.value = AtenderAlertaState.Success(result.data)
                is ApiResult.Error -> _state.value = AtenderAlertaState.Error(result.message)
            }
        }
    }

    // definimos estados al momento de atender la alerta
    private val _atenderState = MutableStateFlow<OperationState>(OperationState.Idle)
    val atenderState = _atenderState.asStateFlow()


    // estados al momento de cambiar el estado de un equipo
    private val _estadoState = MutableStateFlow<OperationState>(OperationState.Idle)
    val estadoState = _estadoState.asStateFlow()

    // funciones para resetear estados a idle
    fun resetAtenderState() {
        _atenderState.value = OperationState.Idle
    }

    fun resetEstadoState() {
        _estadoState.value = OperationState.Idle
    }

    // representacion de atender una alerta
    fun atenderAlerta(alerta: Alerta) {
        viewModelScope.launch {
            _atenderState.value = OperationState.Loading
            // se copia la alerta y solo se cambia el estado
            val updated = alerta.copy(estado = EstadoAlerta.ATENDIDA)
            when (repository.update(updated)) {
                is ApiResult.Success -> {
                    _atenderState.value = OperationState.Success
                }
                is ApiResult.Error -> {
                    _atenderState.value = OperationState.Error("Error")
                }
            }
        }
    }

    // funcion al cambiar el estado de un equipo
    fun cambiarEstado(equipoId: UUID, nuevoEstado: Estado) {
        viewModelScope.launch {
            _estadoState.value = OperationState.Loading
            val result = registroOperativoRepository.cambiarEstado(equipoId, nuevoEstado)
            if (result is ApiResult.Success) {
                _estadoState.value = OperationState.Success
            } else {
                _estadoState.value = OperationState.Error("Error")
            }
        }
    }
}