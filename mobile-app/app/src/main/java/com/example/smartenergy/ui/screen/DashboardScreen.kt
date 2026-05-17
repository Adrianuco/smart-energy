package com.example.smartenergy.ui.screen

import android.R.attr.content
import android.widget.ProgressBar
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartenergy.ui.components.ConsumoGraph

@Composable
fun DashboardScreen(onVerEdificiosClick: () -> Unit, onVerReportesClick: () -> Unit, onVerAlertasClick: () -> Unit) {
    var consumo by remember { mutableStateOf(250) }
    var eficiencia by remember { mutableStateOf(0.8f) }
    var alertas by remember { mutableStateOf(5) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 140.dp, start = 20.dp, end = 20.dp)
    ) {
        Text(
            "Dashboard",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1C1B1F)
        )

        Spacer(modifier = Modifier.height(24.dp))


        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                Text("Consumo Actual",
                    color = Color.Gray,
                    style = MaterialTheme.typography.labelLarge
                )

                Text(
                    "$consumo kWh", fontSize = 36.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF6750A4)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    LinearProgressIndicator(
                        progress = { eficiencia },
                        modifier = Modifier.weight(1f).height(8.dp).clip(CircleShape),
                        color = Color(0xFF6750A4),
                        trackColor = Color(0xFFEADDFF)
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Text("${(eficiencia * 100).toInt()}%", fontWeight = FontWeight.Bold)
                }
                Text(
                    "Eficiencia energética",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(80.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFD8E4)),
                    modifier = Modifier.clickable(onClick = onVerAlertasClick)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp).fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Badge(containerColor = Color.Red) { Text("$alertas", color = Color.White) }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Alertas", fontWeight = FontWeight.Medium)
                    }
                }

                Button(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Reportar", textAlign = TextAlign.Center)
                }

                OutlinedButton(
                    onClick = onVerEdificiosClick,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Ver Edificios")
                }
            }

            Card(
                modifier = Modifier
                    .weight(1f)
                    .height(200.dp)
                    .clickable(
                        onClick = onVerReportesClick
                    ),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)

                ) {
                    Text(
                        "Tendencia Hoy",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleSmall)

                    Text("Consumo en kWh",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray)

                    Spacer(modifier = Modifier.height(16.dp))

                    val datosEjemplo = listOf(10f, 15f, 50f, 100f, 80f, 120f, 90f)

                    ConsumoGraph(listConsumo = datosEjemplo)
                }
            }
        }
    }
}