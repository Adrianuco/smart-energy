package com.example.smartenergy.model

data class Edificio(
    val nombre: String,
    val consumo: Float,
    val estado: String,
    val aulas: List<Aula>
)
