package com.example.smartenergy.viewmodel

import com.example.smartenergy.model.Incidencia
import com.example.smartenergy.repository.IncidenciaRepository

class IncidenciasViewModel(
    private val repository: IncidenciaRepository = IncidenciaRepository()
) {

    fun obtenerIncidencias(): List<Incidencia> {
        return repository.obtenerTodas()
    }

    fun agregarIncidencia(incidencia: Incidencia) {
        repository.agregar(incidencia)
    }

    fun eliminarIncidencia(incidencia: Incidencia) {
        repository.eliminar(incidencia)
    }
}