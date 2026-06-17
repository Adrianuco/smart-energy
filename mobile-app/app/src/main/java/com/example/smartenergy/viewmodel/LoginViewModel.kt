package com.example.smartenergy.viewmodel

import com.example.smartenergy.model.Usuario

class LoginViewModel {

    fun iniciarSesion(
        usuario: Usuario,
        cif: String,
        password: String
    ): Boolean {
        return usuario.cif == cif &&
                usuario.password == password &&
                usuario.activo
    }
}