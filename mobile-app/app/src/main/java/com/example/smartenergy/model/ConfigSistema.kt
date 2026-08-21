package com.example.smartenergy.model

import java.util.UUID

data class ConfigSistema(
    val id: String = UUID.randomUUID().toString(),
    val margenEncendido: Int,
    val tiempoMinimoDesperdicio: Int,
    val activo: Boolean
)