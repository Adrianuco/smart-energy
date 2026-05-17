package com.example.smartenergy.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable


@Serializable object LoginRuta
@Serializable object DashboardRuta
@Serializable object EdificiosRuta

@Serializable object IncidenciaRuta

@Serializable data class DetalleRuta(val edificioNombre: String)

sealed class Tab(val route: Any, val icon: ImageVector, val label: String) {
    object Home : Tab(DashboardRuta, Icons.Default.Home, "Inicio")
}