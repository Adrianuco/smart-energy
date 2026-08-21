package com.example.smartenergy.viewmodel.edificio

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smartenergy.repository.EdificioRepository
import com.example.smartenergy.repository.RegistroOperativoRepository

class DetalleEdificioViewModelFactory(
    private val repository: EdificioRepository,
    private val registroOperativoRepository: RegistroOperativoRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetalleEdificioViewModel(repository, registroOperativoRepository) as T
    }
}
