package com.example.smartenergy.repository

class RegistroConsumoRepository {

    private val registros = mutableListOf<RegistroConsumo>()

    fun obtenerRegistros(): List<RegistroConsumo> {
        return registros
    }

    fun agregarRegistro(registro: RegistroConsumo) {
        registros.add(registro)
    }
}