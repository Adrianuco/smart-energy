package com.example.smartenergy.viewmodel.horarios

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smartenergy.repository.AulaRepository
import com.example.smartenergy.repository.EquipoRepository
import com.example.smartenergy.repository.HorarioAcademicoRepository
import com.example.smartenergy.repository.RegistroOperativoRepository

class HorariosViewModelFactory(
    private val repository: HorarioAcademicoRepository,
    private val aulaRepository: AulaRepository,
    private val equipoRepository: EquipoRepository,
    private val registroOperativoRepository: RegistroOperativoRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HorariosViewModel(repository, aulaRepository, equipoRepository, registroOperativoRepository) as T
    }
}
