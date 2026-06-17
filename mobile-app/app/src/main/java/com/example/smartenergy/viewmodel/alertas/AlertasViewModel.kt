package com.example.smartenergy.viewmodel.alertas

import com.example.smartenergy.model.Alerta
import com.example.smartenergy.repository.AlertaRepository

class AlertasViewModel(
    private val repository: AlertaRepository = AlertaRepository()
) {

    fun obtenerAlertas(): List<Alerta> {
        return repository.obtenerTodas()
    }

    fun agregarAlerta(alerta: Alerta) {
        repository.agregar(alerta)
    }

    fun eliminarAlerta(alerta: Alerta) {
        repository.eliminar(alerta)
    }
}