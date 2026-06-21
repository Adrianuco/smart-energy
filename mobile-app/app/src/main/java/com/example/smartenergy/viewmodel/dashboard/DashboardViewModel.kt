package com.example.smartenergy.viewmodel.dashboard

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartenergy.model.Dashboard
import com.example.smartenergy.model.Edificio
import com.example.smartenergy.model.Equipo
import com.example.smartenergy.repository.DashboardRepository
import com.example.smartenergy.repository.EdificioRepository
import com.example.smartenergy.repository.EquipoRepository
import com.example.smartenergy.service.ApiResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val repository: DashboardRepository
) : ViewModel() {

    private val _state = MutableStateFlow<DashboardState>(DashboardState.Loading)

    val state = _state.asStateFlow()

    init {
        getDashboard()
    }

    private fun getDashboard() {
        viewModelScope.launch {
            when (val result = repository.getDashboard()) {
                is ApiResult.Success -> _state.value = DashboardState.Success(result.data)
                is ApiResult.Error -> _state.value = DashboardState.Error(result.message)
            }
        }
    }
}