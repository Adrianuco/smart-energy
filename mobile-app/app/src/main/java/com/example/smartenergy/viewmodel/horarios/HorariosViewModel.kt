package com.example.smartenergy.viewmodel.horarios

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartenergy.model.HorarioAcademico
import com.example.smartenergy.repository.HorarioAcademicoRepository
import com.example.smartenergy.service.ApiResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class HorariosViewModel(
    private val repository: HorarioAcademicoRepository
) : ViewModel() {

    private val _state = MutableStateFlow<HorariosState>(HorariosState.Loading)
    private val _importState = MutableStateFlow<ImportState>(ImportState.Idle)
    val state = _state.asStateFlow()
    val importState = _importState.asStateFlow()

    init {
        findAll()
    }
    fun findAll() {
        viewModelScope.launch{
            _state.value = HorariosState.Loading
            when(val result = repository.findAll()) {
                is ApiResult.Success -> _state.value = HorariosState.Success(result.data)
                is ApiResult.Error -> _state.value = HorariosState.Error(result.message)
            }
        }
    }

    fun import(file: MultipartBody.Part) {
        viewModelScope.launch {
            _importState.value = ImportState.Loading

            when(val result = repository.import(file)) {
                is ApiResult.Success -> {
                    _importState.value = ImportState.Success(result.data)
                    findAll()
                }
                is ApiResult.Error -> _importState.value = ImportState.Error(result.message)
            }
        }
    }
}