package com.example.smartenergy.viewmodel.horarios

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smartenergy.repository.HorarioAcademicoRepository

class HorariosViewModelFactory(private val repository: HorarioAcademicoRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HorariosViewModel(repository) as T
    }
}
