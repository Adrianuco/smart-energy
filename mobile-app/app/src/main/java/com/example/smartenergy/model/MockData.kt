package com.example.smartenergy.model

import java.time.LocalTime

val listaEquiposAC = listOf(
    EquipoAC("1", "Samsung", "WindFree 12k", 12000, "A++"),
    EquipoAC("2", "LG", "ArtCool 18k", 18000, "A+++"),
    EquipoAC("3", "Midea", "Mission II", 12000, "A+")
)

val listaUsuarios = listOf(
    Usuario("1", "Adriano Almanza", "adriano@uam.edu.ni", Rol.ADMIN),
    Usuario("2", "Juan Pérez", "juan.perez@uam.edu.ni", Rol.TECNICO)
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
