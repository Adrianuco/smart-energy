package com.example.smartenergy.model

import java.time.LocalTime
import java.util.UUID

data class Horario(
    val id: String = UUID.randomUUID().toString(),
    val aulaId: String, // Relación con el aula
    val diaSemana: Int, // 1 (Lunes) a 7 (Domingo)
    val horaInicio: LocalTime,
    val horaFin: LocalTime,
    val materia: String? = null,
    val grupo: String? = null
)
