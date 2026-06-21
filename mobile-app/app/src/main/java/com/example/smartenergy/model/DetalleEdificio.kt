package com.example.smartenergy.model

data class DetalleEdificio(
    val nombre: String,
    val consumo: Double,
    val aulas: List<Aula>? = emptyList()
)
