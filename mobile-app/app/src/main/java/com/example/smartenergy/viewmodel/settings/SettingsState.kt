package com.example.smartenergy.viewmodel.settings

import com.example.smartenergy.model.ConfigSistema
import com.example.smartenergy.model.Edificio


interface SettingsState {
    data object Loading : SettingsState

    data class Success(
        val configSistema: ConfigSistema
    ): SettingsState

    data class Error(val message: String): SettingsState
}