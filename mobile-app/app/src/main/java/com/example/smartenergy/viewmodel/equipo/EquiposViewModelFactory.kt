package com.example.smartenergy.viewmodel.equipo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smartenergy.repository.EquipoRepository

class EquiposViewModelFactory(private val repository: EquipoRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EquiposViewModel(repository) as T
    }
}
