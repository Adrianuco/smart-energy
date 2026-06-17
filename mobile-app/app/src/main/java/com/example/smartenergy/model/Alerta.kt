package com.example.smartenergy.model

import java.util.UUID

data class Alerta(
    val id: String = UUID.randomUUID().toString(),
    val atendida: Boolean,
    val tipoAlerta: String
)