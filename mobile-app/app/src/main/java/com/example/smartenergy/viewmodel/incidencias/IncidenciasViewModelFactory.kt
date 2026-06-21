package com.example.smartenergy.viewmodel.incidencias

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smartenergy.repository.IncidenciaRepository

class IncidenciasViewModelFactory(private val repository: IncidenciaRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return IncidenciasViewModel(repository) as T
    }
}
