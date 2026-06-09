package com.example.smartenergy.model

import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

data class ReporteIncidencia(
    val id: String = UUID.randomUUID().toString(),
    val descripcion: String,
    val fecha: LocalDate,
    val foto: Int,
    val hora: LocalTime,
    val tipoIncidencia: String
)