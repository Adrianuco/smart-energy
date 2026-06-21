package com.example.smartenergy.viewmodel.equipo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smartenergy.repository.EquipoRepository

class RegistrarEquipoViewModelFactory(private val repository: EquipoRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RegistrarEquipoViewModel(repository) as T
    }
}
