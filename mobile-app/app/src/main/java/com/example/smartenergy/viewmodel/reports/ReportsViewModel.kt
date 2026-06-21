package com.example.smartenergy.viewmodel.reports

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartenergy.repository.EdificioRepository
import com.example.smartenergy.service.ApiResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ReportsViewModel(
    private val repository: EdificioRepository
) : ViewModel() {

    private val _state = MutableStateFlow<ReportsState>(ReportsState.Loading)
    val state = _state.asStateFlow()

    init {
        loadEdificios()
    }

    fun loadEdificios() {
        viewModelScope.launch {
            _state.value = ReportsState.Loading
            when (val result = repository.findAll()) {
                is ApiResult.Success -> {
                    _state.value = ReportsState.Success(result.data)
                }
                is ApiResult.Error -> {
                    _state.value = ReportsState.Error(result.message)
                }
            }
        }
    }
}