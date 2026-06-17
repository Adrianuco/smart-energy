package com.example.smartenergy.viewmodel.usuarios

import com.example.smartenergy.model.Usuario

class UsuariosViewModel {

    private val usuarios = mutableListOf<Usuario>()

    fun obtenerUsuarios(): List<Usuario> {
        return usuarios
    }

    fun agregarUsuario(usuario: Usuario) {
        usuarios.add(usuario)
    }

    fun eliminarUsuario(usuario: Usuario) {
        usuarios.remove(usuario)
    }
}