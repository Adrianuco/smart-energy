package com.example.smartenergy.model

import java.time.LocalDateTime
import java.util.UUID


data class Incidencia(
    val id: String? = null,
    val descripcion: String,
    val fechaHora: LocalDateTime = LocalDateTime.now(),
    val tipoIncidencia: String,
    val aula: Aula? = null,
    val estado: EstadoAlerta = EstadoAlerta.PENDIENTE
) {
    val tipo: String get() = tipoIncidencia
    val fecha: String get() = fechaHora.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"))
}
