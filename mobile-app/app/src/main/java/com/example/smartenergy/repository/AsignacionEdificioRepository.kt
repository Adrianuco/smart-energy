package com.example.smartenergy.repository

import com.example.smartenergy.model.AsignacionEdificio

class AsignacionEdificioRepository {

    private val asignaciones = mutableListOf<AsignacionEdificio>()

    fun obtenerTodas(): List<AsignacionEdificio> = asignaciones

    fun agregar(asignacion: AsignacionEdificio) {
        asignaciones.add(asignacion)
    }

    fun eliminar(asignacion: AsignacionEdificio) {
        asignaciones.remove(asignacion)
    }
}