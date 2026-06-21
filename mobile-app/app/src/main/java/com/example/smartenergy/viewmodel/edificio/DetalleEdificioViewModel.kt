package com.example.smartenergy.viewmodel.edificio

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartenergy.repository.AulaRepository
import com.example.smartenergy.repository.EdificioRepository
import com.example.smartenergy.service.ApiResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class DetalleEdificioViewModel(
    private val repository: EdificioRepository,
): ViewModel() {

    private val _state = MutableStateFlow<DetalleEdificioState>(DetalleEdificioState.Loading)

    val state = _state.asStateFlow()

    fun findDetalle(id: UUID) {
        viewModelScope.launch{
            when(val result = repository.findDetalle(id)) {
                is ApiResult.Success -> _state.value = DetalleEdificioState.Success(result.data)
                is ApiResult.Error -> _state.value = DetalleEdificioState.Error(result.message)
            }
        }
    }
}