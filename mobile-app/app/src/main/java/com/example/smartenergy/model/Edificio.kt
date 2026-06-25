package com.example.smartenergy.model

import java.util.UUID

data class Edificio(
    val nombre: String,
    val consumo: Float = 0f,
    val estado: String = "OK",
    val aulas: List<Aula>? = emptyList(),
    val id: String? = null,
    val consumoEsperado: Float = 0f,
    val ahorro: Float = 0f
)
