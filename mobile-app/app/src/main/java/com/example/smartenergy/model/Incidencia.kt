package com.example.smartenergy.model

import java.time.LocalDateTime
import java.util.UUID

enum class EstadoIncidencia {
    PENDIENTE, EN_REVISION, RESUELTA
}

data class Incidencia(
    val id: String = UUID.randomUUID().toString(),
    val descripcion: String,
    val fechaHora: LocalDateTime = LocalDateTime.now(),
    val tipoIncidencia: String,
    val aula: Aula? = null,
    val estado: EstadoIncidencia = EstadoIncidencia.PENDIENTE
) {
    val tipo: String get() = tipoIncidencia
    val fecha: String get() = fechaHora.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"))
}
