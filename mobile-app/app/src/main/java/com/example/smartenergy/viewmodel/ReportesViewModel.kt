package com.example.smartenergy.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.smartenergy.model.EstadoEquipo
import com.example.smartenergy.model.RegistroConsumo
import com.example.smartenergy.repository.EstadoEquipoRepository
import com.example.smartenergy.repository.RegistroConsumoRepository

class ReportesViewModel : ViewModel() {

    private val registroRepository = RegistroConsumoRepository()
    private val estadoRepository = EstadoEquipoRepository()

    var registrosConsumo by mutableStateOf(listOf<RegistroConsumo>())
        private set

    var estadosEquipo by mutableStateOf(listOf<EstadoEquipo>())
        private set

    init {
        cargarReportes()
    }

    fun cargarReportes() {
        registrosConsumo = registroRepository.obtenerRegistros()
        estadosEquipo = estadoRepository.obtenerEstados()
    }

    fun agregarRegistroConsumo(registro: RegistroConsumo) {
        registroRepository.agregarRegistro(registro)
        cargarReportes()
    }

    fun agregarEstadoEquipo(estado: EstadoEquipo) {
        estadoRepository.agregarEstado(estado)
        cargarReportes()
    }
}