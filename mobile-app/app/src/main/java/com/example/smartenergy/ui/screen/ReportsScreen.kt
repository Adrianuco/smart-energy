package com.example.smartenergy.ui.screen

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.example.smartenergy.ui.theme.AppColors
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

// =========================
// DATA MODELS
// =========================

data class EdificioReport(
    val id: String,
    val nombre: String,
    val consumoActual: Float,
    val consumoPromedio: Float,
    val consumoOptimo: Float,
    val tendencia: Float
)

data class ConsumoHistorico(
    val label: String,
    val consumo: Float
)

// =========================
// DATA
// =========================

val edificiosReport = listOf(
    EdificioReport("A", "Edificio A", 285f, 240f, 220f, 12.5f),
    EdificioReport("B", "Edificio B", 365f, 310f, 280f, 8.2f),
    EdificioReport("C", "Edificio C", 325f, 290f, 300f, -3.1f),
    EdificioReport("D", "Edificio D", 245f, 210f, 200f, 15.7f),
    EdificioReport("E", "Edificio E", 195f, 180f, 170f, -2.4f)
)

// =========================
// HISTORIAL
// =========================

fun getHistorico(
    edificioId: String,
    periodo: String
): List<ConsumoHistorico> {

    val baseData = when (edificioId) {
        "A" -> listOf(120f, 180f, 220f, 190f, 250f, 210f, 260f)
        "B" -> listOf(200f, 240f, 280f, 260f, 320f, 290f, 340f)
        "C" -> listOf(180f, 210f, 240f, 220f, 270f, 250f, 300f)
        "D" -> listOf(100f, 140f, 180f, 160f, 200f, 190f, 220f)
        else -> listOf(80f, 110f, 140f, 120f, 160f, 150f, 170f)
    }

    return when (periodo) {
        "Semana" -> {
            baseData.mapIndexed { index, value ->
                ConsumoHistorico(
                    listOf("L", "M", "X", "J", "V", "S", "D")[index],
                    value
                )
            }
        }
        "Mes" -> {
            List(4) { index ->
                ConsumoHistorico(
                    "S${index + 1}",
                    baseData[index] * 1.3f
                )
            }
        }
        else -> {
            List(6) { index ->
                ConsumoHistorico(
                    "${(index + 1) * 5}",
                    baseData[index] * 1.5f
                )
            }
        }
    }
}

// =========================
// SCREEN
// =========================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportsScreen() {

    var edificioSeleccionado by remember {
        mutableStateOf(edificiosReport[0])
    }

    var periodoSeleccionado by remember {
        mutableStateOf("Semana")
    }

    val historico = getHistorico(
        edificioSeleccionado.id,
        periodoSeleccionado
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Reportes",
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = LocalDate.now()
                                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Spacer(modifier = Modifier.height(4.dp))

            // =========================
            // CHIPS
            // =========================

            Text(
                text = "Seleccionar edificio",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(edificiosReport) { edificio ->
                    EdificioChip(
                        edificio = edificio,
                        isSelected = edificio == edificioSeleccionado
                    ) {
                        edificioSeleccionado = edificio
                    }
                }
            }

            // =========================
            // MAIN CARD
            // =========================

            CardPrincipal(edificioSeleccionado)

            // =========================
            // FILTERS
            // =========================

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TiempoChip(
                    text = "Semana",
                    selected = periodoSeleccionado == "Semana"
                ) {
                    periodoSeleccionado = "Semana"
                }

                TiempoChip(
                    text = "Mes",
                    selected = periodoSeleccionado == "Mes"
                ) {
                    periodoSeleccionado = "Mes"
                }

                TiempoChip(
                    text = "30 días",
                    selected = periodoSeleccionado == "30 días"
                ) {
                    periodoSeleccionado = "30 días"
                }
            }

            // =========================
            // GRAPH CARD
            // =========================

            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(2.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "Histórico $periodoSeleccionado",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    GraficoLineasHistorico(historico)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

// =========================
// MAIN CARD
// =========================

@Composable
private fun CardPrincipal(edificio: EdificioReport) {

    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // HEADER
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = edificio.nombre,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${edificio.consumoActual.roundToInt()} kWh",
                        style = MaterialTheme.typography.displayLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = if (edificio.tendencia > 0)
                        AppColors.StatusErrorBackground
                    else
                        AppColors.StatusOkBackground
                ) {
                    Text(
                        text = "${if (edificio.tendencia > 0) "+" else ""}${edificio.tendencia.roundToInt()}%",
                        color = if (edificio.tendencia > 0) AppColors.StatusError else AppColors.StatusOk,
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                    )
                }
            }

            // MINI STATS
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                MiniStatCard(
                    modifier = Modifier.weight(1f),
                    label = "Actual",
                    value = edificio.consumoActual.roundToInt().toString(),
                    color = MaterialTheme.colorScheme.primary
                )

                MiniStatCard(
                    modifier = Modifier.weight(1f),
                    label = "Promedio",
                    value = edificio.consumoPromedio.roundToInt().toString(),
                    color = AppColors.StatusWarning
                )

                MiniStatCard(
                    modifier = Modifier.weight(1f),
                    label = "Óptimo",
                    value = edificio.consumoOptimo.roundToInt().toString(),
                    color = AppColors.StatusOk
                )

                MiniStatCard(
                    modifier = Modifier.weight(1f),
                    label = "Esperado",
                    value = (edificio.consumoOptimo + 15).roundToInt().toString(),
                    color = AppColors.ChartQuaternary
                )
            }
        }
    }
}

// =========================
// MINI CARD
// =========================

@Composable
private fun MiniStatCard(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    color: androidx.compose.ui.graphics.Color
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(1.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                color = color
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

// =========================
// TIME CHIP
// =========================

@Composable
private fun TiempoChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor by animateColorAsState(
        if (selected)
            MaterialTheme.colorScheme.primary
        else
            MaterialTheme.colorScheme.surface,
        label = ""
    )

    val textColor by animateColorAsState(
        if (selected)
            MaterialTheme.colorScheme.onPrimary
        else
            MaterialTheme.colorScheme.onSurfaceVariant,
        label = ""
    )

    Card(
        modifier = Modifier.clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(if (selected) 0.dp else 1.dp),
        border = if (!selected)
            BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
        else null
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                color = textColor,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

// =========================
// GRAPH
// =========================

@Composable
private fun GraficoLineasHistorico(
    historico: List<ConsumoHistorico>
) {
    com.example.smartenergy.ui.components.SmartEnergyLineChart(
        data = historico.map { it.consumo },
        bottomLabels = historico.map { it.label },
        chartHeight = 220.dp,
        showAxis = true
    )
}

// =========================
// BUILDING CHIP
// =========================

@Composable
private fun EdificioChip(
    edificio: EdificioReport,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val borderColor by animateColorAsState(
        if (isSelected)
            MaterialTheme.colorScheme.primary
        else
            MaterialTheme.colorScheme.outlineVariant,
        label = ""
    )

    Card(
        modifier = Modifier.clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(
            if (isSelected) 2.dp else 1.dp,
            borderColor
        ),
        colors = CardDefaults.cardColors(
            containerColor =
                if (isSelected)
                    MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)
                else
                    MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(if (isSelected) 0.dp else 1.dp)
    ) {
        Column(
            modifier = Modifier
                .width(80.dp)
                .padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = edificio.id,
                style = MaterialTheme.typography.titleLarge,
                color = if (isSelected)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "${edificio.consumoActual.roundToInt()}",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

// =========================
// PREVIEW
// =========================

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun PreviewReports() {
    ReportsScreen()
}