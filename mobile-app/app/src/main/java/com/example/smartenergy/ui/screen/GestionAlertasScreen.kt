package com.example.smartenergy.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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

    Scaffold (
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {

            Text(
                text = "Alertas activas y registradas",
                fontSize = 24.sp,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                contentPadding = PaddingValues(bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                items(listaAlertas) { alerta ->

                    Card(
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {

                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {

                            Text(
                                text = "${alerta.aula} | ${alerta.edificio}",
                                style = MaterialTheme.typography.titleMedium
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "Tipo: ${alerta.tipo}",
                                style = MaterialTheme.typography.bodyMedium
                            )

                            Text(
                                text = "Hora: ${alerta.hora}",
                                style = MaterialTheme.typography.bodyMedium
                            )

                            Text(
                                text = "Estado: ${alerta.estado}",
                                style = MaterialTheme.typography.bodyMedium
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Button(
                                onClick = { },
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text("Atender Alerta")
                            }
                        }
                    }
                }
            }
        }
    }
}