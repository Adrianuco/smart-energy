package com.example.smartenergy.repository

class EstadoEquipoRepository {

    private val estados = mutableListOf<EstadoEquipo>()

    fun obtenerEstados(): List<EstadoEquipo> = estados

    fun agregarEstado(estado: EstadoEquipo) {
        estados.add(estado)
    }
}

