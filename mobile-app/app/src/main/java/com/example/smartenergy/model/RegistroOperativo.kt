package com.example.smartenergy.model

import java.time.LocalDateTime
import java.util.UUID

data class RegistroOperativo(
    val id: String? = null,
    val estado: Estado,
    val consumo: Double,
    val inicio: LocalDateTime,
    val fin: LocalDateTime? = null,
    val equipo: Equipo? = null
)
