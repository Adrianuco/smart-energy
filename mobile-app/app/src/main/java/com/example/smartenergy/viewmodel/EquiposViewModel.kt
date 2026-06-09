package com.example.smartenergy.viewmodel

import androidx.lifecycle.ViewModel
import com.example.smartenergy.repository.EquipoRepository

class EquiposViewModel : ViewModel() {

    private val repository = EquipoRepository()

    val equipos = repository.obtenerEquipos()
}