package com.example.smartenergy.ui.screen


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartenergy.model.Aula
import com.example.smartenergy.model.Edificio

@Composable
fun DetalleEdificioScreen(edificio: Edificio) {

    val edificioActual = edificio.nombre
    val porcentaje = 80
    val consumo = edificio.consumo


    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 140.dp, start = 20.dp, end = 20.dp),
            verticalArrangement = Arrangement.spacedBy(50.dp)
        ) {

            Column {

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = edificioActual,
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(65.dp))

                Box(
                    modifier = Modifier
                        .size(180.dp)
                        .align(Alignment.CenterHorizontally),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                    progress = { porcentaje / 100f },
                    modifier = Modifier.fillMaxSize(),
                    color = ProgressIndicatorDefaults.circularColor,
                    strokeWidth = 12.dp,
                    trackColor = ProgressIndicatorDefaults.circularIndeterminateTrackColor,
                    strokeCap = ProgressIndicatorDefaults.CircularDeterminateStrokeCap,
                    )

                    Text(
                        text = "$porcentaje%",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            ) {

                Column(modifier = Modifier.weight(1.2f)) {

                    Card(
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(20.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF4CAF50)),
                                contentAlignment = Alignment.Center
                            ) {

                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column {
                                Text("Consumo")
                                Spacer(modifier = Modifier.height(4.dp))
                                Text("$consumo kWh", fontWeight = FontWeight.Bold)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(28.dp))

                    Card(
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(20.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Warning, contentDescription = null)
                            Spacer(modifier = Modifier.width(12.dp))
                            Text("Advertencias")
                        }
                    }

                    Spacer(modifier = Modifier.height(28.dp))

                    Card(
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(20.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Spacer(modifier = Modifier.width(12.dp))
                            Text("Alertas")
                        }
                    }
                }

                Spacer(modifier = Modifier.width(24.dp))

                Column(modifier = Modifier.weight(0.8f)) {
                    edificio.aulas.forEach {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Info, contentDescription = null)
                            Spacer(modifier = Modifier.width(10.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(it.nombre)
                                Spacer(modifier = Modifier.height(2.dp))
                                Text("${it.eficiencia}%")
                            }
                        }
                    }
                }
            }
        }
    }
}