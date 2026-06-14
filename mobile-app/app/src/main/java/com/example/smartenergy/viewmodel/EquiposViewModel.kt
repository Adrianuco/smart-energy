package com.example.smartenergy.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.smartenergy.model.Equipo
import com.example.smartenergy.repository.EquipoRepository

class EquiposViewModel : ViewModel() {

    private val repository = EquipoRepository()

    var equipos by mutableStateOf(listOf<Equipo>())
        private set

    init {
        cargarEquipos()
    }

    fun cargarEquipos() {
        equipos = repository.obtenerEquipos()
    }

    fun agregarEquipo(equipo: Equipo) {
        repository.agregarEquipo(equipo)
        cargarEquipos()
    }

    fun buscarEquipoPorId(id: Int): Equipo? {
        return repository.buscarEquipoPorId(id)
    }

    fun actualizarEquipo(equipo: Equipo) {
        repository.actualizarEquipo(equipo)
        cargarEquipos()
    }

    fun eliminarEquipo(id: Int) {
        repository.eliminarEquipo(id)
        cargarEquipos()
    }
}