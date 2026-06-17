package com.example.smartenergy.viewmodel.edificio

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.smartenergy.model.Edificio
import com.example.smartenergy.repository.EdificioRepository

class EdificiosViewModel : ViewModel() {

    private val repository = EdificioRepository()

    var edificios by mutableStateOf(listOf<Edificio>())
        private set

    init {
        cargarEdificios()
    }

    fun cargarEdificios() {
        edificios = repository.obtenerEdificios()
    }

    fun agregarEdificio(edificio: Edificio) {
        repository.agregarEdificio(edificio)
        cargarEdificios()
    }

    fun buscarEdificioPorNombre(nombre: String): Edificio? {
        return repository.buscarEdificioPorNombre(nombre)
    }

    fun actualizarEdificio(edificio: Edificio) {
        repository.actualizarEdificio(edificio)
        cargarEdificios()
    }

    fun eliminarEdificio(nombre: String) {
        repository.eliminarEdificio(nombre)
        cargarEdificios()
    }
}