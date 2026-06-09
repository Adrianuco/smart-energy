package com.example.smartenergy.viewmodel

import androidx.lifecycle.ViewModel
import com.example.smartenergy.repository.EdificioRepository

class EdificiosViewModel : ViewModel() {

    private val repository = EdificioRepository()

    val edificios = repository.obtenerEdificios()
}