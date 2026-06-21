package com.example.smartenergy.viewmodel.alertas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartenergy.repository.AlertaRepository
import com.example.smartenergy.service.ApiResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class AtenderAlertaViewModel(
    private val repository: AlertaRepository
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

}