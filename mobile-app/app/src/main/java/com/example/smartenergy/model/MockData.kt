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
    Aula("Aula A-101", 1, 60f, EquipoAC("AC-A1", "LG", "Split", 12000, "A++", true, 0.5f, 2.5f)),
    Aula("Aula A-104", 1, 80f, EquipoAC("AC-A2", "Samsung", "WindFree", 12000, "A+++", true, 0.4f, 2.2f)),
    Aula("Aula A-202", 2, 40f, EquipoAC("AC-A3", "Panasonic", "Inverter", 9000, "A+", false, 0.6f, 3.0f)),
)
val listaAulasEdficioB = listOf(
    Aula("Aula B-101", 1, 60f, EquipoAC("AC-B1", "Carrier", "Comfort", 18000, "A++", true, 0.8f, 3.5f)),
    Aula("Aula B-104", 1, 80f, EquipoAC("AC-B2", "Daikin", "SkyAir", 12000, "A+++", true, 0.5f, 2.8f)),
    Aula("Aula B-202", 2, 40f, EquipoAC("AC-B3", "Trane", "Voyager", 24000, "A", true, 0.9f, 4.0f)),
)
val listaAulasEdficioC = listOf(
    Aula("Aula C-101", 1, 60f, EquipoAC("AC-C1", "York", "Affinity", 12000, "A++", true, 0.7f, 3.2f)),
    Aula("Aula C-104", 1, 80f, EquipoAC("AC-C2", "Lennox", "Elite", 12000, "A+", true, 0.6f, 2.9f)),
    Aula("Aula C-202", 2, 40f, EquipoAC("AC-C3", "Midea", "Mission II", 9000, "A++", true, 0.5f, 2.4f)),
)
val listaAulasEdficioD = listOf(
    Aula("Aula D-101", 1, 60f, EquipoAC("AC-D1", "LG", "Dual Inverter", 12000, "A+++", true, 0.4f, 2.3f)),
    Aula("Aula D-104", 1, 80f, EquipoAC("AC-D2", "Samsung", "Digital Inverter", 12000, "A++", true, 0.4f, 2.2f)),
    Aula("Aula D-202", 2, 40f, EquipoAC("AC-D3", "Panasonic", "Nanoe", 9000, "A+++", true, 0.6f, 2.7f)),
)
val listaAulasEdficioE = listOf(
    Aula("Aula E-101", 1, 60f, EquipoAC("AC-E1", "Carrier", "Extreme", 36000, "A", true, 1.0f, 4.5f)),
    Aula("Aula E-104", 1, 80f, EquipoAC("AC-E2", "Daikin", "Ururu Sarara", 24000, "A+++", true, 0.8f, 3.8f)),
    Aula("Aula E-202", 2, 40f, EquipoAC("AC-E3", "York", "LX Series", 12000, "A++", true, 0.7f, 3.2f)),
)
val listaEdificios = listOf(
    Edificio("Edificio A", 240f ,"OK", listaAulasEdficioA),
    Edificio("Edificio B", 240f, "Problemas", listaAulasEdficioB),
    Edificio("Edificio C", 240f, "Advertencias", listaAulasEdficioC),
    Edificio("Edificio D", 240f, "OK", listaAulasEdficioD),
    Edificio("Edificio E", 240f, "Problemas", listaAulasEdficioE),
)
