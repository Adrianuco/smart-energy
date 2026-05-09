package com.example.smartenergy.ui.screen

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

// Datos mejorados con horarios académicos reales
data class ConsumoEdificio(
    val nombre: String,
    val consumo: Float,
    val consumoEsperado: Float,
    val alerta: Boolean,
    val ocupacion: String
)

data class ConsumoHorario(
    val hora: String,
    val consumo: Float,
    val clasesProgramadas: Boolean,
    val tipo: String
)

data class DesperdicioEnergetico(
    val edificio: String,
    val horasMuertas: Int,
    val consumoDesperdicio: Float,
    val porcentajeDesperdicio: Float
)

val edificiosData = listOf(
    ConsumoEdificio("Edificio A ", 285f, 220f, true, "Alta"),
    ConsumoEdificio("Edificio B - ", 365f, 280f, true, "Alta"),
    ConsumoEdificio("Edificio F - ", 325f, 300f, false, "Alta"),
    ConsumoEdificio("Edificio G - ", 245f, 200f, true, "Media"),
    ConsumoEdificio("Edificio H - ", 195f, 180f, false, "Media"),
    ConsumoEdificio("Edificio I - ", 175f, 160f, false, "Baja"),
    ConsumoEdificio("Edificio E - ", 165f, 140f, true, "Baja"),
    ConsumoEdificio("Edificio D - ", 115f, 100f, false, "FueraHorario")
)

val horariosData = listOf(
    ConsumoHorario("06:00", 25f, false, "FueraHorario"),
    ConsumoHorario("08:00", 85f, true, "Clases"),
    ConsumoHorario("10:00", 145f, true, "Clases"),
    ConsumoHorario("12:00", 95f, false, "EntreClases"),
    ConsumoHorario("14:00", 165f, true, "Clases"),
    ConsumoHorario("16:00", 135f, true, "Clases"),
    ConsumoHorario("18:00", 75f, false, "EntreClases"),
    ConsumoHorario("20:00", 45f, false, "FueraHorario"),
    ConsumoHorario("22:00", 35f, false, "FueraHorario")
)

val desperdiciosData = listOf(
    DesperdicioEnergetico("Edificio B", 18, 45.5f, 12.5f),
    DesperdicioEnergetico("Edificio A", 14, 32.8f, 9.2f),
    DesperdicioEnergetico("Edificio F", 22, 58.3f, 15.8f),
    DesperdicioEnergetico("Edificio G", 9, 21.4f, 6.1f)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportsScreen() {
    var filtro by remember { mutableStateOf("Todos") }
    var ordenMayor by remember { mutableStateOf(true) }
    var showAlertas by remember { mutableStateOf(false) }

    val edificiosFiltrados = edificiosData
        .filter {
            when (filtro) {
                "Todos" -> true
                "Con Alerta" -> it.alerta
                "Fuera Horario" -> it.ocupacion == "FueraHorario"
                else -> true
            }
        }
        .sortedByDescending { if (ordenMayor) it.consumo else -it.consumo }

    Scaffold(
        topBar = {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF2196F3))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Reportes Energéticos",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    // Badge de alertas
                    if (showAlertas) {
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .background(Color.Red, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "3",
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        },

    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item { HeaderPrincipal() }
            item { FiltrosSection(filtro, ordenMayor) { newFiltro, newOrden -> filtro = newFiltro; ordenMayor = newOrden } }

            item {
                Text(
                    "🏆 Ranking Edificios",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
            items(edificiosFiltrados) { edificio ->
                TarjetaRankingEdificio(edificio)
            }

            item {
                Text(
                    "📊 Consumo por Horario Académico",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            }
            item { GraficaBarrasHorarios(horariosData) }

            item {
                Text(
                    "⚠️ Horas Muertas & Desperdicio",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            }
            items(desperdiciosData) { desperdicio ->
                TarjetaDesperdicio(desperdicio)
            }

            item { Spacer(modifier = Modifier.height(100.dp)) }
        }
    }
}

@Composable
private fun HeaderPrincipal() {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F9FF)),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                "Reportes Energéticos Inteligentes",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1A237E)
            )
            Text(
                "Monitoreo en tiempo real del consumo eléctrico de edificios UAM.",
                color = Color(0xFF424242),
                fontSize = 16.sp
            )
        }
    }
}

@Composable
private fun FiltrosSection(
    filtro: String,
    ordenMayor: Boolean,
    onFiltroChange: (String, Boolean) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            listOf("Todos", "Con Alerta", "Fuera Horario").forEach { filtroOp ->
                val isSelected = filtro == filtroOp
                val colorAnim by animateColorAsState(
                    if (isSelected) Color(0xFF2196F3) else Color(0xFFE0E0E0),
                    tween(300)
                )

                Card(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onFiltroChange(filtroOp, ordenMayor) },
                    colors = CardDefaults.cardColors(containerColor = colorAnim),
                    elevation = CardDefaults.cardElevation(if (isSelected) 8.dp else 2.dp)
                ) {
                    Box(
                        modifier = Modifier.padding(12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            filtroOp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Ordenar por:", fontWeight = FontWeight.Medium)

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OrdenamientoButton("Mayor Consumo", ordenMayor) { onFiltroChange(filtro, true) }
                OrdenamientoButton("Menor Consumo", !ordenMayor) { onFiltroChange(filtro, false) }
            }
        }
    }
}

@Composable
private fun OrdenamientoButton(text: String, selected: Boolean, onClick: () -> Unit) {
    val colorAnim by animateColorAsState(
        if (selected) Color(0xFFF44336) else Color.Gray,
        tween(200)
    )

    Card(
        modifier = Modifier.clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = colorAnim),
        elevation = CardDefaults.cardElevation(if (selected) 6.dp else 2.dp)
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text, color = Color.White, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
private fun TarjetaRankingEdificio(edificio: ConsumoEdificio) {
    val exceso = edificio.consumo - edificio.consumoEsperado
    val colorCard = when {
        edificio.alerta -> Color(0xFFFFEBEE)
        exceso > 0 -> Color(0xFFFFF3E0)
        else -> Color(0xFFE8F5E8)
    }

    Card(
        colors = CardDefaults.cardColors(containerColor = colorCard),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .shadow(8.dp, CircleShape)
                    .background(
                        when {
                            edificio.alerta -> Color(0xFFF44336)
                            edificio.ocupacion == "FueraHorario" -> Color(0xFFFF9800)
                            else -> Color(0xFF4CAF50)
                        }, CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    when {
                        edificio.alerta -> "⚠️"
                        edificio.ocupacion == "FueraHorario" -> "⏰"
                        else -> "✅"
                    },
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    edificio.nombre,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                    Text(
                        text = "${edificio.ocupacion} ocupación",
                        fontSize = 14.sp,
                        color =
                            when (edificio.ocupacion) {

                                "Alta" -> Color(0xFFF44336)

                                "Media" -> Color(0xFFFF9800)

                                "Baja" -> Color(0xFF4CAF50)

                                else -> Color.Gray
                            },
                        fontWeight = FontWeight.Bold
                    )

            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    "${edificio.consumo.toInt()} kWh",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A237E)
                )
                Text(
                    if (exceso > 0) "+${exceso.toInt()} kWh" else "${exceso.toInt()} kWh",
                    fontSize = 14.sp,
                    color = if (exceso > 0) Color(0xFFF44336) else Color(0xFF4CAF50)
                )
            }
        }
    }
}

@Composable
private fun GraficaBarrasHorarios(horarios: List<ConsumoHorario>) {
    val maxConsumo = horarios.maxOf { it.consumo }

    Card(
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            horarios.forEach { horario ->
                val anchoBarra = ((horario.consumo / maxConsumo) * 300f).dp
                val colorBarra = when {
                    !horario.clasesProgramadas && horario.consumo > 30f -> Color(0xFFF44336)
                    horario.consumo > 120f -> Color(0xFFFF9800)
                    else -> Color(0xFF4CAF50)
                }

                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            horario.hora,
                            fontWeight = FontWeight.Medium,
                            color = if (horario.clasesProgramadas) Color.Black else Color.Gray
                        )
                        Text(
                            "${horario.consumo.toInt()} kWh",
                            fontWeight = FontWeight.Bold,
                            color = colorBarra
                        )
                    }

                    Box(
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .height(20.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFFF5F5F5))
                    ) {
                        Box(
                            modifier = Modifier
                                .height(20.dp)
                                .width(anchoBarra)
                                .clip(RoundedCornerShape(10.dp))
                                .background(colorBarra)
                        )
                    }

                    Text(
                        text = horario.tipo,
                        fontSize = 12.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 4.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
private fun TarjetaDesperdicio(desperdicio: DesperdicioEnergetico) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE)),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(Color(0xFFF44336), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text("⬇️", fontSize = 24.sp, color = Color.White)
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    desperdicio.edificio,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "${desperdicio.horasMuertas}h de consumo en horas muertas",
                    color = Color(0xFF424242)
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    "${desperdicio.consumoDesperdicio.toInt()} kWh",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFF44336)
                )
                Text(
                    "${desperdicio.porcentajeDesperdicio.toInt()}%",
                    fontSize = 14.sp,
                    color = Color(0xFFF44336),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReportsScreenPreview() {
    ReportsScreen()
}