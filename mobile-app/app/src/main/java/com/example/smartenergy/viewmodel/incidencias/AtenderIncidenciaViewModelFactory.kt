package com.example.smartenergy.viewmodel.incidencias

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smartenergy.repository.IncidenciaRepository

class AtenderIncidenciaViewModelFactory(private val repository: IncidenciaRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AtenderIncidenciaViewModel(repository) as T
    }
}
