package com.example.smartenergy.viewmodel.alertas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smartenergy.repository.AlertaRepository
import com.example.smartenergy.repository.RegistroOperativoRepository

class AtenderAlertaViewModelFactory(
    private val repository: AlertaRepository,
    private val registroOperativoRepository: RegistroOperativoRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AtenderAlertaViewModel(repository, registroOperativoRepository) as T
    }
}
