package com.example.smartenergy.repository

import com.example.smartenergy.model.ConfigSistema

class ConfigSistemaRepository {

    private var configuracion: ConfigSistema? = null

    fun obtenerConfiguracion(): ConfigSistema? = configuracion

    fun guardarConfiguracion(config: ConfigSistema) {
        configuracion = config
    }
}