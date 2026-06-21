package com.example.smartenergy.viewmodel.reports

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smartenergy.repository.EdificioRepository

class ReportsViewModelFactory(private val repository: EdificioRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ReportsViewModel(repository) as T
    }
}
