package com.example.smartenergy.repository

import com.example.smartenergy.model.ReporteIncidencia

class ReporteIncidenciaRepository {

    private val reportes = mutableListOf<ReporteIncidencia>()

    fun obtenerTodos(): List<ReporteIncidencia> = reportes

    fun agregar(reporte: ReporteIncidencia) {
        reportes.add(reporte)
    }

    fun eliminar(reporte: ReporteIncidencia) {
        reportes.remove(reporte)
    }
}