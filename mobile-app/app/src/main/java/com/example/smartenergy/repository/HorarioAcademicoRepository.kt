package com.example.smartenergy.repository

import com.example.smartenergy.model.HorarioAcademico

class HorarioAcademicoRepository {

    private val horarios = mutableListOf<HorarioAcademico>()

    fun obtenerHorarios(): List<HorarioAcademico> = horarios

    fun agregarHorario(horario: HorarioAcademico) {
        horarios.add(horario)
    }

    fun buscarHorarioPorId(id: Int): HorarioAcademico? {
        return horarios.find { it.id == id }
    }

    fun actualizarHorario(horario: HorarioAcademico) {
        val index = horarios.indexOfFirst { it.id == horario.id }
        if (index != -1) {
            horarios[index] = horario
        }
    }

    fun eliminarHorario(id: Int) {
        horarios.removeIf { it.id == id }
    }
}