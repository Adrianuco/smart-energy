package com.example.smartenergy.model

import java.time.LocalTime

val listaEquipos = listOf(
    Equipo("1", "Samsung", "WindFree 12k", 12000, "A++", true, 0.4, 2.2),
    Equipo("2", "LG", "ArtCool 18k", 18000, "A+++", true, 0.6, 3.5),
    Equipo("3", "Midea", "Mission II", 12000, "A+", false, 0.5, 2.8)
)

val listaUsuarios = listOf(
    Administrador("1", "Adriano", "Almanza", "ADM001", "password", true, "5"),
    ApoyoLogistico("2", "Juan", "Pérez", "TEC001", "password", true)
)

val listaHorariosAcademico = listOf(
    HorarioAcademico(
        id = "1",
        diaSemana = 1,
        horaInicio = LocalTime.of(8, 0),
        horaFin = LocalTime.of(9, 40),
        asignatura = "Programación III"
    ),
    HorarioAcademico(
        id = "2",
        diaSemana = 3,
        horaInicio = LocalTime.of(8, 0),
        horaFin = LocalTime.of(9, 40),
        asignatura = "Programación III"
    )
)

val listaAulasEdficioA = listOf(
    Aula(id = "A-101", codigo = "Aula A-101", piso = 1, eficiencia = 60f, equipo = listaEquipos[0]),
    Aula(id = "A-104", codigo = "Aula A-104", piso = 1, eficiencia = 80f, equipo = listaEquipos[1]),
    Aula(id = "A-202", codigo = "Aula A-202", piso = 2, eficiencia = 40f, equipo = listaEquipos[2])
)

val listaAulasEdficioB = listOf(
    Aula(id = "B-101", codigo = "Aula B-101", piso = 1, eficiencia = 60f, equipo = listaEquipos[0]),
    Aula(id = "B-104", codigo = "Aula B-104", piso = 1, eficiencia = 80f, equipo = listaEquipos[1]),
    Aula(id = "B-202", codigo = "Aula B-202", piso = 2, eficiencia = 40f, equipo = listaEquipos[2])
)

val listaAulasEdficioC = listOf(
    Aula(id = "C-101", codigo = "Aula C-101", piso = 1, eficiencia = 60f, equipo = listaEquipos[0]),
    Aula(id = "C-104", codigo = "Aula C-104", piso = 1, eficiencia = 80f, equipo = listaEquipos[1]),
    Aula(id = "C-202", codigo = "Aula C-202", piso = 2, eficiencia = 40f, equipo = listaEquipos[2])
)

val listaAulasEdficioD = listOf(
    Aula(id = "D-101", codigo = "Aula D-101", piso = 1, eficiencia = 60f, equipo = listaEquipos[0]),
    Aula(id = "D-104", codigo = "Aula D-104", piso = 1, eficiencia = 80f, equipo = listaEquipos[1]),
    Aula(id = "D-202", codigo = "Aula D-202", piso = 2, eficiencia = 40f, equipo = listaEquipos[2])
)

val listaAulasEdficioE = listOf(
    Aula(id = "E-101", codigo = "Aula E-101", piso = 1, eficiencia = 60f, equipo = listaEquipos[0]),
    Aula(id = "E-104", codigo = "Aula E-104", piso = 1, eficiencia = 80f, equipo = listaEquipos[1]),
    Aula(id = "E-202", codigo = "Aula E-202", piso = 2, eficiencia = 40f, equipo = listaEquipos[2])
)

val listaEdificios = listOf(
    Edificio("Edificio A", 240f, "OK", listaAulasEdficioA),
    Edificio("Edificio B", 240f, "Problemas", listaAulasEdficioB),
    Edificio("Edificio C", 240f, "Advertencias", listaAulasEdficioC),
    Edificio("Edificio D", 240f, "OK", listaAulasEdficioD),
    Edificio("Edificio E", 240f, "Problemas", listaAulasEdficioE)
)