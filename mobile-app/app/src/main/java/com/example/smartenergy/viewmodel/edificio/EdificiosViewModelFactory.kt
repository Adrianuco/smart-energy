package com.example.smartenergy.viewmodel.edificio

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smartenergy.repository.EdificioRepository

class EdificiosViewModelFactory(private val repository: EdificioRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EdificiosViewModel(repository) as T
    }
}
