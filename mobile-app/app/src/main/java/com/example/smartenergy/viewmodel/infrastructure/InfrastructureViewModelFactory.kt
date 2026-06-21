package com.example.smartenergy.viewmodel.infrastructure

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smartenergy.repository.AulaRepository
import com.example.smartenergy.repository.EdificioRepository
import com.example.smartenergy.repository.EquipoRepository

class InfrastructureViewModelFactory(
    private val equipoRepository: EquipoRepository,
    private val edificioRepository: EdificioRepository,
    private val aulaRepository: AulaRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return InfrastructureViewModel(equipoRepository, edificioRepository, aulaRepository) as T
    }
}
