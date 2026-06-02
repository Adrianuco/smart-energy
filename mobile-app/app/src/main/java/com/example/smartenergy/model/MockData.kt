package com.example.smartenergy.model

import java.time.LocalTime

val listaEquiposAC = listOf(
    EquipoAC("1", "Samsung", "WindFree 12k", 12000, "A++", true, 0.4f, 2.2f),
    EquipoAC("2", "LG", "ArtCool 18k", 18000, "A+++", true, 0.6f, 3.5f),
    EquipoAC("3", "Midea", "Mission II", 12000, "A+", false, 0.5f, 2.8f)
)

val listaUsuarios = listOf(
    Administrador("1", "Adriano", "Almanza", "ADM001", "password", true, 5),
    ApoyoLogistico("2", "Juan", "Pérez", "TEC001", "password", true)
)

val listaHorarios = listOf(
    Horario(
        id = "1",
        aulaId = "B-101",
        diaSemana = 1,
        horaInicio = LocalTime.of(8, 0),
        horaFin = LocalTime.of(9, 40),
        materia = "Programación III",
        grupo = "0801"
    ),
    Horario(
        id = "2",
        aulaId = "B-101",
        diaSemana = 3,
        horaInicio = LocalTime.of(8, 0),
        horaFin = LocalTime.of(9, 40),
        materia = "Programación III",
        grupo = "0801"
    )
)
