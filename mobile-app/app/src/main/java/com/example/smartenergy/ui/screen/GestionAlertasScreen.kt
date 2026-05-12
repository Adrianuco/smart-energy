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

val listaAlertas = listOf(
    Alerta("Aula B-104", "Edificio B", "Apagar AC", "2:30 PM", "Pendiente"),
    Alerta("Aula C-202", "Edificio C", "Revisar consumo", "3:15 PM", "Advertencia"),
    Alerta("Aula E-101", "Edificio E", "Apagar AC", "5:00 PM", "Pendiente"),
    Alerta("Aula A-104", "Edificio A", "Todo correcto", "1:00 PM", "Resuelta"),
    Alerta("Aula D-202", "Edificio D", "Encender AC", "7:45 AM", "Pendiente")
)

@Composable
fun GestionAlertasScreen() {
    Text("Gestión de Alertas")
}