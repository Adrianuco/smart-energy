package com.example.smartenergy.model

import java.time.LocalDate
import java.time.LocalTime

data class RegistroConsumo(
    val id: Int,
    val consumo: Double,
    val fecha: LocalDate,
    val horaInicio: LocalTime,
    val horaFin: LocalTime
)