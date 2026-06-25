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
import java.util.UUID

class AtenderAlertaViewModel(
    private val repository: AlertaRepository,
    private val registroOperativoRepository: RegistroOperativoRepository
): ViewModel() {

    private val _state = MutableStateFlow<AtenderAlertaState>(AtenderAlertaState.Loading)

    val state = _state.asStateFlow()

    fun findById(id: UUID) {
        viewModelScope.launch{
            when(val result = repository.findById(id)) {
                is ApiResult.Success -> _state.value = AtenderAlertaState.Success(result.data)
                is ApiResult.Error -> _state.value = AtenderAlertaState.Error(result.message)
            }
        }
    }

    fun atenderAlerta(alerta: Alerta, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val updated = alerta.copy(estado = EstadoAlerta.ATENDIDA)
            when (repository.update(updated)) {
                is ApiResult.Success -> {
                    onResult(true)
                }
                is ApiResult.Error -> onResult(false)
            }
        }
    }

    fun cambiarEstado(equipoId: UUID, nuevoEstado: Estado, callback: () -> Unit = {}) {
        viewModelScope.launch {
            val result = registroOperativoRepository.cambiarEstado(equipoId, nuevoEstado)
            if (result is ApiResult.Success) {
                callback()
            }
        }
    }
}