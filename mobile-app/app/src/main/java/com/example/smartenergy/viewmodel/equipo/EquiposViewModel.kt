package com.example.smartenergy.viewmodel.equipo

import android.view.View
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartenergy.model.Equipo
import com.example.smartenergy.repository.EquipoRepository
import com.example.smartenergy.service.ApiResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EquiposViewModel(
    private val repository: EquipoRepository
): ViewModel() {

    private val _state = MutableStateFlow<EquiposState>(EquiposState.Loading)

    val state = _state.asStateFlow()

    init {
        findAll()
    }

    private fun findAll() {
        viewModelScope.launch{
            _state.value = EquiposState.Loading
            when(val result = repository.findAll()) {
                is ApiResult.Success -> _state.value = EquiposState.Success(result.data)
                is ApiResult.Error -> _state.value = EquiposState.Error(result.message)
            }
        }
    }
}