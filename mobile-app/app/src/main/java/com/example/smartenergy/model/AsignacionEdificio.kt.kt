package com.example.smartenergy.model

import java.time.LocalDateTime
import java.util.UUID

data class `AsignacionEdificio.kt`(
    val id: String = UUID.randomUUID().toString(),
    val activo: Boolean,
    val fechaAsignacion: LocalDateTime
)