package com.example.smartenergy.model

data class EquipoAC(
    val id: String,
    val marca: String,
    val modelo: String,
    val btu: Int,
    val eficiencia: String // Ej: SEER 16, Clase A++
)
