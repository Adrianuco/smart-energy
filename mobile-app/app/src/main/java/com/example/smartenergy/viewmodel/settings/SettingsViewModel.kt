package com.example.smartenergy.viewmodel.settings

import com.example.smartenergy.model.ConfigSistema
import com.example.smartenergy.repository.ConfigSistemaRepository

class SettingsViewModel(
    private val repository: ConfigSistemaRepository = ConfigSistemaRepository()
) {

    fun obtenerConfiguracion(): ConfigSistema? {
        return repository.obtenerConfiguracion()
    }

    fun guardarConfiguracion(config: ConfigSistema) {
        repository.guardarConfiguracion(config)
    }
}