package com.example.smartenergy.ui.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

data class Alerta(
    val aula: String,
    val edificio: String,
    val tipo: String,
    val hora: String,
    val estado: String
)

@Composable
fun GestionAlertasScreen() {
    Text("Gestión de Alertas")
}