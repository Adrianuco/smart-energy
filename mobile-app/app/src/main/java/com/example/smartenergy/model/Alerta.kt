package com.example.smartenergy.model

import java.time.LocalDateTime
import java.util.UUID

data class Alerta(
    val id: String = UUID.randomUUID().toString(),
    val tipoAlerta: String,
    val estado: EstadoAlerta,
    val aula: Aula? = null,
    val fechaHora: LocalDateTime = LocalDateTime.now()
) {
    val tipo: String get() = tipoAlerta
    val hora: String get() = fechaHora.format(java.time.format.DateTimeFormatter.ofPattern("h:mm a"))
}