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

val listaAulasEdficioA = listOf(
    Aula(id = "A-101", nombre = "Aula A-101"),
    Aula(id = "A-104", nombre = "Aula A-104"),
    Aula(id = "A-202", nombre = "Aula A-202")
)

val listaAulasEdficioB = listOf(
    Aula(id = "B-101", nombre = "Aula B-101"),
    Aula(id = "B-104", nombre = "Aula B-104"),
    Aula(id = "B-202", nombre = "Aula B-202")
)

val listaAulasEdficioC = listOf(
    Aula(id = "C-101", nombre = "Aula C-101"),
    Aula(id = "C-104", nombre = "Aula C-104"),
    Aula(id = "C-202", nombre = "Aula C-202")
)

val listaAulasEdficioD = listOf(
    Aula(id = "D-101", nombre = "Aula D-101"),
    Aula(id = "D-104", nombre = "Aula D-104"),
    Aula(id = "D-202", nombre = "Aula D-202")
)

val listaAulasEdficioE = listOf(
    Aula(id = "E-101", nombre = "Aula E-101"),
    Aula(id = "E-104", nombre = "Aula E-104"),
    Aula(id = "E-202", nombre = "Aula E-202")
)

val listaEdificios = listOf(
    Edificio("Edificio A", 240f, "OK", listaAulasEdficioA),
    Edificio("Edificio B", 240f, "Problemas", listaAulasEdficioB),
    Edificio("Edificio C", 240f, "Advertencias", listaAulasEdficioC),
    Edificio("Edificio D", 240f, "OK", listaAulasEdficioD),
    Edificio("Edificio E", 240f, "Problemas", listaAulasEdficioE)
)