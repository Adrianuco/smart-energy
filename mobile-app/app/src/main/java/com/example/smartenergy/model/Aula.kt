package com.example.smartenergy.model

data class Aula(
    val id: String,
    val nombre: String,
    val edificio: Edificio? = null,
    val horarios: List<HorarioAcademico> = emptyList()
)

