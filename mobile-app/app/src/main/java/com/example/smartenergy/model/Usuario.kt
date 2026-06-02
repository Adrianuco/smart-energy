package com.example.smartenergy.model

enum class Rol {
    ADMINISTRADOR, APOYO_LOGISTICO
}

open class Usuario(
    open val id: String,
    open val nombre: String,
    open val apellido: String,
    open val cif: String,
    open val password: String,
    open val rol: Rol,
    open val activo: Boolean
)

data class ApoyoLogistico(
    override val id: String,
    override val nombre: String,
    override val apellido: String,
    override val cif: String,
    override val password: String,
    override val activo: Boolean
) : Usuario(id, nombre, apellido, cif, password, Rol.APOYO_LOGISTICO, activo)

data class Administrador(
    override val id: String,
    override val nombre: String,
    override val apellido: String,
    override val cif: String,
    override val password: String,
    override val activo: Boolean,
    val nivelAcceso: Int
) : Usuario(id, nombre, apellido, cif, password, Rol.ADMINISTRADOR, activo)
