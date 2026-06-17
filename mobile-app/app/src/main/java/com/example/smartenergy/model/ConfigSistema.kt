package com.example.smartenergy.model

data class ConfigSistema(
    val eficienciaMinimaAceptable: Double,
    val margenEncendido: Double,
    val tiempoMinimoDesperdicio: Double
)