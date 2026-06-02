package com.example.smartenergy.model

data class EquipoAC(
    val id: String,
    val marca: String,
    val modelo: String,
    val btu: Int,
    val eficiencia: String,
    val operativo: Boolean,
    val potenciaMinima: Float,
    val potenciaNominal: Float
)
