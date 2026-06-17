package com.example.smartenergy.model

import java.util.UUID

data class Equipo(
    val id: String = UUID.randomUUID().toString(),
    val marca: String,
    val modelo: String,
    val btu: Int,
    val eficiencia: String,
    val operativo: Boolean,
    val potenciaMinima: Double,
    val potenciaNominal: Double,
    val aula: Aula? = null
)
