package com.example.smartenergy.repository

import com.example.smartenergy.model.Alerta

class AlertaRepository {

    private val alertas = mutableListOf<Alerta>()

    fun obtenerTodas(): List<Alerta> = alertas

    fun agregar(alerta: Alerta) {
        alertas.add(alerta)
    }

    fun eliminar(alerta: Alerta) {
        alertas.remove(alerta)
    }
}