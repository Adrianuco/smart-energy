package com.example.smartenergy.repository

import com.example.smartenergy.model.EstadoEquipo

class EstadoEquipoRepository {

    private val estados = mutableListOf<EstadoEquipo>()

    fun obtenerEstados(): List<EstadoEquipo> = estados

    fun agregarEstado(estado: EstadoEquipo) {
        estados.add(estado)
    }
}

