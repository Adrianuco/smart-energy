package com.example.smartenergy.viewmodel.usuarios

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartenergy.model.Administrador
import com.example.smartenergy.model.ApoyoLogistico
import com.example.smartenergy.repository.AdministradorRepository
import com.example.smartenergy.repository.ApoyoLogisticoRepository
import com.example.smartenergy.service.ApiResult
import com.example.smartenergy.viewmodel.OperationState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UsuariosViewModel(
    private val adminRepository: AdministradorRepository,
    private val logisticaRepository: ApoyoLogisticoRepository
): ViewModel() {

    private val _state = MutableStateFlow<UsuariosState>(UsuariosState.Loading)
    val state = _state.asStateFlow()

    init {
        loadUsuarios()
    }

    fun loadUsuarios() {
        viewModelScope.launch {
            _state.value = UsuariosState.Loading
            val adminResult = adminRepository.findAll()
            val logResult = logisticaRepository.findAll()

            if (adminResult is ApiResult.Success && logResult is ApiResult.Success) {
                _state.value = UsuariosState.Success(
                    administradores = adminResult.data,
                    usuariosLogistica = logResult.data
                )
            } else {
                val adminMsg = (adminResult as? ApiResult.Error)?.message ?: ""
                val logMsg = (logResult as? ApiResult.Error)?.message ?: ""
                _state.value = UsuariosState.Error(
                    "Error al cargar usuarios. Admin: $adminMsg, Logística: $logMsg"
                )
            }
        }
    }

    private val _mutationState = MutableStateFlow<OperationState>(OperationState.Idle)
    val mutationState = _mutationState.asStateFlow()

    fun resetMutationState() {
        _mutationState.value = OperationState.Idle
    }

    fun agregarAdministrador(administrador: Administrador) {
        viewModelScope.launch {
            _mutationState.value = OperationState.Loading
            when (adminRepository.save(administrador)) {
                is ApiResult.Success -> {
                    loadUsuarios()
                    _mutationState.value = OperationState.Success
                }
                is ApiResult.Error -> {
                    _mutationState.value = OperationState.Error("Error al registrar Administrador")
                }
            }
        }
    }

    fun agregarLogistico(logistico: ApoyoLogistico) {
        viewModelScope.launch {
            _mutationState.value = OperationState.Loading
            when (logisticaRepository.save(logistico)) {
                is ApiResult.Success -> {
                    loadUsuarios()
                    _mutationState.value = OperationState.Success
                }
                is ApiResult.Error -> {
                    _mutationState.value = OperationState.Error("Error al registrar Apoyo Logístico")
                }
            }
        }
    }
}