package com.example.smartenergy.viewmodel.alertas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smartenergy.repository.AlertaRepository

class AlertasViewModelFactory(private val repository: AlertaRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AlertasViewModel(repository) as T
    }
}
