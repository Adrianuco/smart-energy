package com.example.smartenergy.viewmodel

import androidx.lifecycle.ViewModel
import com.example.smartenergy.repository.EdificioRepository

class DashboardViewModel : ViewModel() {

    private val repository = EdificioRepository()

    val edificios = repository.obtenerEdificios()
}

