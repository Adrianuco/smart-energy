package com.example.smartenergy.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

data class ReporteEnergia(
    val titulo: String,
    val valor: String,
    val color: Color
)

val listaReportes = listOf(
    ReporteEnergia(
        "Consumo Diario",
        "120 kWh",
        Color(0xFF4CAF50)
    ),
    ReporteEnergia(
        "Alertas Activas",
        "5 Alertas",
        Color(0xFFFF9800)
    ),
    ReporteEnergia(
        "Aulas Monitoreadas",
        "12 Aulas",
        Color(0xFF2196F3)
    ),
    ReporteEnergia(
        "Equipos Fuera de Horario",
        "2 Equipos",
        Color(0xFFF44336)
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportsScreen() {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Reportes")
                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(top = 120.dp, start = 20.dp, end = 20.dp)
        ) {

            Text(
                text = "Panel de Control",
                fontSize = 38.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Supervisión de equipos y consumo",
                color = Color.Gray,
                modifier = Modifier.padding(top = 4.dp, bottom = 20.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                items(listaReportes) { reporte ->

                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = reporte.color.copy(alpha = 0.15f)
                        ),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 4.dp
                        )
                    ) {

                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Icon(
                                imageVector = when (reporte.titulo) {
                                    "Consumo Diario" -> Icons.Default.Info
                                    "Alertas Activas" -> Icons.Default.Warning
                                    "Aulas Monitoreadas" -> Icons.Default.Home
                                    else -> Icons.Default.Settings
                                },
                                contentDescription = null,
                                tint = reporte.color
                            )

                            Text(
                                text = reporte.titulo,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(top = 8.dp)
                            )

                            Text(
                                text = reporte.valor,
                                fontSize = 20.sp,
                                color = reporte.color,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(top = 8.dp)


                            )

                        }

                    }

                }

            }

        }

    }

}
@Preview(showBackground = true)
@Composable
fun ReportsScreenPreview() {
    ReportsScreen()
}