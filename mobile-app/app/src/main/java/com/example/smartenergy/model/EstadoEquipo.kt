package com.example.smartenergy.model

import java.time.LocalDate
import java.time.LocalTime

data class EstadoEquipo(
    val estado: String,
    val fecha: LocalDate,
    val horaInicio: LocalTime,
    val horaFin: LocalTime
)












