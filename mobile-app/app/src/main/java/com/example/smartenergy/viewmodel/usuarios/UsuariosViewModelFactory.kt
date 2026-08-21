package com.example.smartenergy.viewmodel.usuarios

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smartenergy.repository.AdministradorRepository
import com.example.smartenergy.repository.ApoyoLogisticoRepository

class UsuariosViewModelFactory(
    private val adminRepository: AdministradorRepository,
    private val logisticaRepository: ApoyoLogisticoRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UsuariosViewModel(adminRepository, logisticaRepository) as T
    }
}
