package com.example.smartenergy.viewmodel.usuarios

import com.example.smartenergy.model.Administrador
import com.example.smartenergy.model.ApoyoLogistico
import com.example.smartenergy.model.Edificio

interface UsuariosState {
    data object Loading : UsuariosState

    data class Success(
        val administradores: List<Administrador>,
        val usuariosLogistica: List<ApoyoLogistico>
    ): UsuariosState

    data class Error(val message: String): UsuariosState
}