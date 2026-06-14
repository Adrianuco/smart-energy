package com.example.smartenergy.repository

import com.example.smartenergy.model.Equipo

class EquipoRepository {

    private val equipos = mutableListOf<Equipo>()

    fun obtenerEquipos(): List<Equipo> = equipos

    fun agregarEquipo(equipo: Equipo) {
        equipos.add(equipo)
    }

    fun buscarEquipoPorId(id: Int): Equipo? {
        return equipos.find { it.id == id }
    }

    fun actualizarEquipo(equipo: Equipo) {
        val index = equipos.indexOfFirst { it.id == equipo.id }
        if (index != -1) {
            equipos[index] = equipo
        }
    }

    fun eliminarEquipo(id: Int) {
        equipos.removeIf { it.id == id }
    }
}