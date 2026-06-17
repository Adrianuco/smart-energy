package com.example.smartenergy.model

data class Aula(
    val id: String,
    val piso: Int = 1,
    val eficiencia: Float = 0f,
    val equipo: Equipo? = null,
    val codigo: String = id,
    val edificio: Edificio? = null,
    val horarios: List<HorarioAcademico> = emptyList(),
    val nombre: String = codigo
)
