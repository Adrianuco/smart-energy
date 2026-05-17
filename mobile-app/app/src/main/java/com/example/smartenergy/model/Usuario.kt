package com.example.smartenergy.model

enum class Rol {
    ADMIN, TECNICO
}

data class Usuario(
    val id: String,
    val nombre: String,
    val email: String,
    val rol: Rol
)
