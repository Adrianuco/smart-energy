package com.example.smartenergy.model

import java.time.LocalTime
import java.util.UUID

data class HorarioAcademico(
    val id: String = UUID.randomUUID().toString(),
    val asignatura: String,
    val diaSemana: Int,
    val horaInicio: LocalTime,
    val horaFin: LocalTime,
    val aula: Aula? = null
)
