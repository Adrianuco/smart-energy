package com.example.smartenergy.model

data class Dashboard(
    val consumoActual: Double,
    val ahorro: Double,
    val kwhAhorrados: Double = 0.0,
    val alertasActivas: Long,
    val edificios: Long,
    val equiposActivos: Long,
    val incidencias: Long,
    val consumoUltimasHoras: List<Double>,
    val horas: List<String>
)
