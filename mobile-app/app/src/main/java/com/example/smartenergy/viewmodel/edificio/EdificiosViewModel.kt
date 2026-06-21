package com.example.smartenergy.viewmodel.edificio

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartenergy.model.Edificio
import com.example.smartenergy.repository.EdificioRepository
import com.example.smartenergy.service.ApiResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EdificiosViewModel(
    private val repository: EdificioRepository
): ViewModel() {
    private val _state = MutableStateFlow<EdificiosState>(EdificiosState.Loading)

    val state = _state.asStateFlow()

    init {
        findAll()
    }

    private fun findAll() {
        viewModelScope.launch{
            _state.value = EdificiosState.Loading
            when(val result = repository.findAll()) {
                is ApiResult.Success -> _state.value = EdificiosState.Success(result.data)
                is ApiResult.Error -> _state.value = EdificiosState.Error(result.message)
            }
        }
    }
}