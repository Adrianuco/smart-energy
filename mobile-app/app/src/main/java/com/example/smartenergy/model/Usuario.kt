package com.example.smartenergy.model

enum class Rol {
    ADMINISTRADOR, APOYO_LOGISTICO
}

interface Usuario {
    val id: String?
    val nombre: String
    val apellido: String
    val cif: String
    val password: String
    val rol: Rol
    val activo: Boolean
}

data class ApoyoLogistico(
    override val id: String? = null,
    override val nombre: String,
    override val apellido: String,
    override val cif: String,
    override val password: String,
    override val activo: Boolean
) : Usuario {
    override val rol: Rol get() = Rol.APOYO_LOGISTICO
}

data class Administrador(
    override val id: String? = null,
    override val nombre: String,
    override val apellido: String,
    override val cif: String,
    override val password: String,
    override val activo: Boolean,
    val nivelAcceso: String
) : Usuario {
    override val rol: Rol get() = Rol.ADMINISTRADOR
}
