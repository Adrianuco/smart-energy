package com.example.smartenergy.model

data class Aula(
    val nombre: String,
    val piso: Int,
    val eficiencia: Float,
    val equipo: EquipoAC? = null
)
