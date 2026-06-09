package com.example.smartenergy.viewmodel

import androidx.lifecycle.ViewModel
import com.example.smartenergy.repository.HorarioAcademicoRepository

class HorariosViewModel : ViewModel() {

    private val repository = HorarioAcademicoRepository()

    val horarios = repository.obtenerHorarios()
}


