package com.example.detalleedificioscreen.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Edificio(val nombre: String, val porcentaje: Int)

@Composable
fun DetalleEdificioScreen() {

    val edificioActual = "Edificio B"
    val porcentaje = 80
    val consumo = 100

    val edificios = listOf(
        Edificio("Aula B-101", 60),
        Edificio("Aula B-104", 80),
        Edificio("Aula B-202", 40),
        Edificio("Aula B-302", 90),
        Edificio("Aula B-306", 70)
    )

    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.SpaceBetween
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
                        progress = porcentaje / 100f,
                        strokeWidth = 12.dp,
                        modifier = Modifier.fillMaxSize()
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
                                    .background(Color(0xFFD1C4E9)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("A")
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
                            Icon(Icons.Default.Error, contentDescription = null)
                            Spacer(modifier = Modifier.width(12.dp))
                            Text("Alertas")
                        }
                    }
                }

                Spacer(modifier = Modifier.width(24.dp))

                Column(modifier = Modifier.weight(0.8f)) {
                    edificios.forEach {
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
                                Text("${it.porcentaje}%")
                            }
                        }
                    }
                }
            }
        }
    }
}