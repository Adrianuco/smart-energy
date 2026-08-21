package com.example.smartenergy.viewmodel.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smartenergy.repository.DashboardRepository

class DashboardViewModelFactory(private val repository: DashboardRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DashboardViewModel(repository) as T
    }
}
