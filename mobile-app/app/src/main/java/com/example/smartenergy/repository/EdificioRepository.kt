package com.example.smartenergy.repository

import com.example.smartenergy.model.Edificio

class EdificioRepository {

    private val edificios = mutableListOf<Edificio>()

    fun obtenerEdificios(): List<Edificio> = edificios

    fun agregarEdificio(edificio: Edificio) {
        edificios.add(edificio)
    }

    fun buscarEdificioPorNombre(nombre: String): Edificio? {
        return edificios.find { it.nombre == nombre }
    }

    fun actualizarEdificio(edificio: Edificio) {
        val index = edificios.indexOfFirst { it.nombre == edificio.nombre }
        if (index != -1) {
            edificios[index] = edificio
        }
    }

    fun eliminarEdificio(nombre: String) {
        edificios.removeIf { it.nombre == nombre }
    }
}
