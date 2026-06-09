package com.example.smartenergy.viewmodel

import com.example.smartenergy.model.ReporteIncidencia
import com.example.smartenergy.repository.ReporteIncidenciaRepository

class IncidenciasViewModel(
    private val repository: ReporteIncidenciaRepository = ReporteIncidenciaRepository()
) {

    fun obtenerIncidencias(): List<ReporteIncidencia> {
        return repository.obtenerTodos()
    }

    fun agregarIncidencia(reporte: ReporteIncidencia) {
        repository.agregar(reporte)
    }

    fun eliminarIncidencia(reporte: ReporteIncidencia) {
        repository.eliminar(reporte)
    }
}