package com.example.smartenergy.repository

import com.example.smartenergy.model.Incidencia

class IncidenciaRepository {

    private val incidencias = mutableListOf<Incidencia>()

    fun obtenerTodas(): List<Incidencia> = incidencias

    fun agregar(incidencia: Incidencia) {
        incidencias.add(incidencia)
    }

    fun eliminar(incidencia: Incidencia) {
        incidencias.remove(incidencia)
    }
}
