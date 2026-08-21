package com.example.smartenergy.model

data class DetalleEdificio(
    val nombre: String,
    val consumo: Double,
    val ahorro: Double = 0.0,
    val aulas: List<Aula>? = emptyList()
)
