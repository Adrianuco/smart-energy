package com.example.smartenergy.model

import java.time.LocalDate
import java.util.UUID

data class AsignacionEdificio(
    val id: String = UUID.randomUUID().toString(),
    val activo: Boolean,
    val fechaAsignacion: LocalDate,
    val apoyoLogistica: ApoyoLogistico? = null,
    val edificio: Edificio? = null
)
