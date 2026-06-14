package com.example.smartenergy.repository

import com.example.smartenergy.model.Aula

class AulaRepository {

    private val aulas = mutableListOf<Aula>()

    fun obtenerAulas(): List<Aula> = aulas

    fun agregarAula(aula: Aula) {
        aulas.add(aula)
    }

    fun buscarAulaPorId(id: String): Aula? {
        return aulas.find { it.id == id }
    }

    fun actualizarAula(aula: Aula) {
        val index = aulas.indexOfFirst { it.id == aula.id }
        if (index != -1) {
            aulas[index] = aula
        }
    }

    fun eliminarAula(id: String) {
        aulas.removeIf { it.id == id }
    }
}
