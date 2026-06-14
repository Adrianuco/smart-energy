package com.example.smartenergy.model

import java.time.LocalTime

data class HorarioAcademico(
    val id: Int,
    val asignatura: String,
    val diaSemana: Int,
    val horaInicio: LocalTime,
    val horaFin: LocalTime
)
