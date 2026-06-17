package com.example.smartenergy.viewmodel.horarios

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.smartenergy.model.HorarioAcademico
import com.example.smartenergy.repository.HorarioAcademicoRepository

class HorariosViewModel : ViewModel() {

    private val repository = HorarioAcademicoRepository()

    var horarios by mutableStateOf(listOf<HorarioAcademico>())
        private set

    init {
        cargarHorarios()
    }

    fun cargarHorarios() {
        horarios = repository.obtenerHorarios()
    }

    fun agregarHorario(horario: HorarioAcademico) {
        repository.agregarHorario(horario)
        cargarHorarios()
    }

    fun buscarHorarioPorId(id: String): HorarioAcademico? {
        return repository.buscarHorarioPorId(id)
    }

    fun actualizarHorario(horario: HorarioAcademico) {
        repository.actualizarHorario(horario)
        cargarHorarios()
    }

    fun eliminarHorario(id: String) {
        repository.eliminarHorario(id)
        cargarHorarios()
    }
}