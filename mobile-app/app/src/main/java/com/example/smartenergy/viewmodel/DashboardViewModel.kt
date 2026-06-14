package com.example.smartenergy.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.smartenergy.model.Edificio
import com.example.smartenergy.model.Equipo
import com.example.smartenergy.repository.EdificioRepository
import com.example.smartenergy.repository.EquipoRepository

class DashboardViewModel : ViewModel() {

    private val edificioRepository = EdificioRepository()
    private val equipoRepository = EquipoRepository()

    var edificios by mutableStateOf(listOf<Edificio>())
        private set

    var equipos by mutableStateOf(listOf<Equipo>())
        private set

    init {
        cargarDatos()
    }

    fun cargarDatos() {
        edificios = edificioRepository.obtenerEdificios()
        equipos = equipoRepository.obtenerEquipos()
    }
}