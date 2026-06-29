package com.example.smartenergy.ui.screen

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ShowChart
import androidx.compose.material.icons.automirrored.outlined.TrendingDown
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.outlined.Bolt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartenergy.ui.components.reports.EdificioChip
import com.example.smartenergy.ui.components.reports.GraficoLineasHistorico
import com.example.smartenergy.ui.components.reports.ReportMiniStat
import com.example.smartenergy.ui.theme.AppColors
import com.example.smartenergy.viewmodel.reports.ReportsState
import com.example.smartenergy.viewmodel.reports.ReportsViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.roundToInt

// =========================
// DATA MODELS
// =========================

data class EdificioReport(
    val id: String,
    val nombre: String,
    val consumoActual: Float,
    val consumoPeorEscenario: Float,
    val ahorroLogrado: Float,
    val tendencia: Float
)

data class ConsumoHistorico(
    val label: String,
    val consumo: Float
)



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportsScreen(
    viewModel: ReportsViewModel
) {
    val state by viewModel.state.collectAsState()
    var periodoSeleccionado by remember { mutableStateOf("Semana") }
    var fechaSeleccionada by remember { mutableStateOf(LocalDate.now()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Reportes de Ahorro",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        when (val currentState = state) {
            ReportsState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is ReportsState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Error: ${currentState.message}", color = MaterialTheme.colorScheme.error)
                }
            }
            is ReportsState.Success -> {
                val edificiosReport = currentState.edificios.map { ed ->
                    val ahorroLogrado = kotlin.math.max(0f, ed.consumoEsperado - ed.consumo)
                    EdificioReport(
                        id = ed.id ?: "",
                        nombre = ed.nombre,
                        consumoActual = ed.consumo,
                        consumoPeorEscenario = ed.consumoEsperado,
                        ahorroLogrado = ahorroLogrado,
                        tendencia = 10f
                    )
                }
                if (edificiosReport.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize().padding(padding),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No hay edificios registrados")
                    }
                } else {
                    var edificioSeleccionado by remember { mutableStateOf(edificiosReport[0]) }

                    // Si la lista cambia, aseguramos de tener un edificio válido seleccionado
                    if (edificiosReport.none { it.id == edificioSeleccionado.id }) {
                        edificioSeleccionado = edificiosReport[0]
                    }

                    LaunchedEffect(edificioSeleccionado.id, periodoSeleccionado) {
                        viewModel.loadHistorico(edificioSeleccionado.id, periodoSeleccionado)
                    }

                    val historicoData by viewModel.historico.collectAsState()

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                            .verticalScroll(rememberScrollState())
                            .padding(horizontal = 20.dp),
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        Spacer(modifier = Modifier.height(4.dp))

                        // Building Selector
                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            Text(
                                text = "Seleccionar Edificio",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                contentPadding = PaddingValues(bottom = 4.dp)
                            ) {
                                items(edificiosReport) { edificio ->
                                    EdificioChip(edificio = edificio, isSelected = (edificio.id == edificioSeleccionado.id)) {
                                        edificioSeleccionado = edificio
                                    }
                                }
                            }
                        }

                        // Main Saving Card (Inspired by Dashboard Hero)
                        Card(
                            shape = RoundedCornerShape(24.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Column(modifier = Modifier.padding(24.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = edificioSeleccionado.nombre,
                                        style = MaterialTheme.typography.titleMedium,
                                        color = Color.White.copy(alpha = 0.8f)
                                    )
                                    Surface(
                                        shape = RoundedCornerShape(12.dp),
                                        color = Color.White.copy(alpha = 0.2f)
                                    ) {
                                        Text(
                                            text = "Eficiente",
                                            color = Color.White,
                                            style = MaterialTheme.typography.labelSmall,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                Row(verticalAlignment = Alignment.Bottom) {
                                    Text(
                                        text = String.format(Locale.US, "%.1f", edificioSeleccionado.ahorroLogrado),
                                        style = MaterialTheme.typography.displayMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "kWh ahorrados",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = Color.White.copy(alpha = 0.7f),
                                        modifier = Modifier.padding(bottom = 12.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    ReportMiniStat(
                                        Modifier.weight(1f),
                                        "Consumo Real",
                                        "${edificioSeleccionado.consumoActual.toInt()} kWh",
                                        Icons.Outlined.Bolt
                                    )
                                    ReportMiniStat(
                                        Modifier.weight(1f),
                                        "Escenario Base",
                                        "${edificioSeleccionado.consumoPeorEscenario.toInt()} kWh",
                                        Icons.AutoMirrored.Outlined.ShowChart
                                    )
                                }
                            }
                        }

                        // Time Period Selection
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(16.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                                .padding(4.dp),
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            listOf("Hoy", "Semana", "Mes").forEach { periodo ->
                                val isSelected = periodoSeleccionado == periodo
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(if (isSelected) MaterialTheme.colorScheme.background else Color.Transparent)
                                        .clickable { periodoSeleccionado = periodo }
                                        .padding(vertical = 8.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = periodo,
                                        style = MaterialTheme.typography.labelLarge,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }

                        // Historical Chart Card
                        Card(
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            elevation = CardDefaults.cardElevation(2.dp)
                        ) {
                            Column(modifier = Modifier.padding(20.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Histórico de Consumo",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Icon(
                                        Icons.AutoMirrored.Outlined.TrendingDown,
                                        contentDescription = null,
                                        tint = AppColors.StatusOk
                                    )
                                }
                                Spacer(modifier = Modifier.height(24.dp))
                                val labels = when (periodoSeleccionado) {
                                    "Hoy" -> listOf("6h", "5h", "4h", "3h", "2h", "1h", "Actual")
                                    "Semana" -> listOf("L", "M", "X", "J", "V", "S", "D")
                                    else -> listOf("S1", "S2", "S3", "S4")
                                }
                                val chartData = historicoData.mapIndexed { index, value ->
                                    ConsumoHistorico(
                                        label = labels.getOrElse(index) { "" },
                                        consumo = value.toFloat()
                                    )
                                }
                                GraficoLineasHistorico(chartData)
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}
