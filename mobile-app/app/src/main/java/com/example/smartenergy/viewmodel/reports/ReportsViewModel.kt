package com.example.smartenergy.viewmodel.reports

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.smartenergy.repository.RegistroOperativoRepository
import com.example.smartenergy.repository.RegistroConsumoRepository

class ReportsViewModel : ViewModel() {

    private val registroRepository = RegistroConsumoRepository()
    private val estadoRepository = RegistroOperativoRepository()

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