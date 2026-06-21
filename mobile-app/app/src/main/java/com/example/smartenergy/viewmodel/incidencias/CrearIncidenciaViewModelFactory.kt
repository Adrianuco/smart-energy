package com.example.smartenergy.viewmodel.incidencias

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smartenergy.repository.AulaRepository
import com.example.smartenergy.repository.IncidenciaRepository

class CrearIncidenciaViewModelFactory(
    private val repository: IncidenciaRepository,
    private val aulaRepository: AulaRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CrearIncidenciaViewModel(repository, aulaRepository) as T
    }
}
