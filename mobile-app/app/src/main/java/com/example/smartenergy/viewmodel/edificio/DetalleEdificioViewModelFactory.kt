package com.example.smartenergy.viewmodel.edificio

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smartenergy.repository.EdificioRepository

class DetalleEdificioViewModelFactory(private val repository: EdificioRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetalleEdificioViewModel(repository) as T
    }
}
