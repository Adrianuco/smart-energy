package com.example.smartenergy.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable


@Serializable object LoginRuta
@Serializable object DashboardRuta
@Serializable object EdificiosRuta
@Serializable object AjustesRuta
@Serializable object HorariosRuta

@Serializable
object ReportesRuta{
}

@Serializable object AlertasRuta
@Serializable data class InfraestructuraRuta(val edificioNombre: String? = null)

@Serializable object IncidenciaRuta

@Serializable data class DetalleRuta(val edificioNombre: String)

sealed class Tab(val route: Any, val icon: ImageVector, val label: String) {
    object Home : Tab(DashboardRuta, Icons.Default.Home, "Inicio")
    object Horarios : Tab(HorariosRuta, Icons.Default.Schedule, "Horarios")
    object Settings : Tab(AjustesRuta, Icons.Default.Settings, "Ajustes")
}