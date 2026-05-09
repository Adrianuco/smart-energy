package com.example.smartenergy.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Reporte(
    val titulo: String,
    val descripcion: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportsScreen() {

    val reportes = listOf(
        Reporte(
            "Consumo diario",
            "El consumo energético estimado de hoy es de 120 kWh"
        ),
        Reporte(
            "Alertas activas",
            "Actualmente existen 5 alertas activas en el sistema"
        ),
        Reporte(
            "Aulas monitoreadas",
            "12 aulas están siendo monitoreadas actualmente"
        ),
        Reporte(
            "Equipos fuera de horario",
            "2 aires acondicionados permanecen encendidos fuera de horario"
        ),
        Reporte(
            "Consumo semanal",
            "El consumo semanal estimado es de 860 kWh"
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Reportes")
                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {


                        }

                    }

                }

