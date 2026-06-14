package com.example.smartenergy.repository

import com.example.smartenergy.model.RegistroConsumo

class RegistroConsumoRepository {

    private val registros = mutableListOf<RegistroConsumo>()

    fun obtenerRegistros(): List<RegistroConsumo> {
        return registros
    }

    fun agregarRegistro(registro: RegistroConsumo) {
        registros.add(registro)
    }
}