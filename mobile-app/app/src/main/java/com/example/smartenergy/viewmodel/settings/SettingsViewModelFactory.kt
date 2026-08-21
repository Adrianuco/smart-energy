package com.example.smartenergy.viewmodel.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smartenergy.repository.ConfigSistemaRepository

class SettingsViewModelFactory(private val repository: ConfigSistemaRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SettingsViewModel(repository) as T
    }
}
