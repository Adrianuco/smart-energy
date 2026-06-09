package com.example.smartenergy.viewmodel

import com.example.smartenergy.model.ConfigSistema
import com.example.smartenergy.repository.ConfigSistemaRepository

class ConfiguracionViewModel(
    private val repository: ConfigSistemaRepository = ConfigSistemaRepository()
) {

    fun obtenerConfiguracion(): ConfigSistema? {
        return repository.obtenerConfiguracion()
    }

    fun guardarConfiguracion(config: ConfigSistema) {
        repository.guardarConfiguracion(config)
    }
}