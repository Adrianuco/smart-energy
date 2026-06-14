package com.example.smartenergy.model

import java.time.LocalTime
import java.util.UUID

data class Horario(
    val id: String = UUID.randomUUID().toString(),
    val aulaId: String,
    val diaSemana: Int,
    val horaInicio: LocalTime,
    val horaFin: LocalTime,
    val materia: String? = null,
    val grupo: String? = null
)