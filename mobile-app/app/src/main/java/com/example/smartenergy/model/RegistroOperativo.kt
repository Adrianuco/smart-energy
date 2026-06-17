package com.example.smartenergy.model

import java.time.LocalDateTime
import java.util.UUID

data class RegistroOperativo(
    val id: String = UUID.randomUUID().toString(),
    val estado: Estado,
    val consumo: Double,
    val inicio: LocalDateTime,
    val fin: LocalDateTime,
    val equipo: Equipo? = null
)
