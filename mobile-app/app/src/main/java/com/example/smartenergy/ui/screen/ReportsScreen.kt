package com.example.smartenergy.ui.screen

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

// =========================
// DATA MODELS
// =========================

data class Edificio(
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

val edificios = listOf(
    Edificio("A", "Edificio A", 285f, 240f, 220f, 12.5f),
    Edificio("B", "Edificio B", 365f, 310f, 280f, 8.2f),
    Edificio("C", "Edificio C", 325f, 290f, 300f, -3.1f),
    Edificio("D", "Edificio D", 245f, 210f, 200f, 15.7f),
    Edificio("E", "Edificio E", 195f, 180f, 170f, -2.4f)
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
        mutableStateOf(edificios[0])
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

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = "⚡ SmartEnergy",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Text(
                            text = LocalDate.now()
                                .format(
                                    DateTimeFormatter.ofPattern("dd/MM/yyyy")
                                ),
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 14.sp
                        )
                    }
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1565C0)
                )
            )
        },

        containerColor = Color(0xFFF4F7FC)

    ) { padding ->

        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp),

            verticalArrangement = Arrangement.spacedBy(24.dp)

        ) {

            // =========================
            // TITLE
            // =========================

            Text(
                text = "Seleccionar edificio",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )

            // =========================
            // CHIPS
            // =========================

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {

                items(edificios) { edificio ->

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
                horizontalArrangement = Arrangement.spacedBy(12.dp)
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
                shape = RoundedCornerShape(28.dp),
                elevation = CardDefaults.cardElevation(12.dp),
                modifier = Modifier.shadow(
                    20.dp,
                    RoundedCornerShape(28.dp)
                )
            ) {

                Column(
                    modifier = Modifier.padding(24.dp)
                ) {

                    Text(
                        text = "Histórico $periodoSeleccionado",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1A1A1A)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    GraficoLineasHistorico(historico)
                }
            }
        }
    }
}

// =========================
// MAIN CARD
// =========================

@Composable
private fun CardPrincipal(edificio: Edificio) {

    Card(
        shape = RoundedCornerShape(28.dp),
        elevation = CardDefaults.cardElevation(14.dp),
        modifier = Modifier.shadow(
            24.dp,
            RoundedCornerShape(28.dp)
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(28.dp),

            verticalArrangement = Arrangement.spacedBy(22.dp)
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
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1A1A1A)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "${edificio.consumoActual.roundToInt()} kWh",
                        fontSize = 40.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF2196F3)
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(18.dp))
                        .background(
                            Color(0xFF2196F3).copy(alpha = 0.12f)
                        )
                        .padding(
                            horizontal = 18.dp,
                            vertical = 14.dp
                        )
                ) {

                    Text(
                        text = "${edificio.tendencia.roundToInt()}%",
                        color = Color(0xFF2196F3),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
            }

            // MINI STATS
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                MiniStatCard(
                    label = "Actual",
                    value = edificio.consumoActual.roundToInt().toString(),
                    color = Color(0xFF2196F3)
                )

                MiniStatCard(
                    label = "Promedio",
                    value = edificio.consumoPromedio.roundToInt().toString(),
                    color = Color(0xFFFF9800)
                )

                MiniStatCard(
                    label = "Óptimo",
                    value = edificio.consumoOptimo.roundToInt().toString(),
                    color = Color(0xFF4CAF50)
                )

                MiniStatCard(
                    label = "Esperado",
                    value = (edificio.consumoOptimo + 15)
                        .roundToInt()
                        .toString(),
                    color = Color(0xFF9C27B0)
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
    label: String,
    value: String,
    color: Color
) {

    Card(
        shape = RoundedCornerShape(22.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        modifier = Modifier
            .width(82.dp)
            .height(88.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),

            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = value,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 18.sp,
                color = color
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = label,
                fontSize = 11.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Medium
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
            Color(0xFF2196F3)
        else
            Color.White,
        label = ""
    )

    val textColor by animateColorAsState(
        if (selected)
            Color.White
        else
            Color(0xFF64748B),
        label = ""
    )

    Card(
        modifier = Modifier
            .clickable { onClick() }
            .shadow(8.dp, RoundedCornerShape(22.dp)),

        shape = RoundedCornerShape(22.dp),

        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),

        border = BorderStroke(
            1.dp,
            if (selected)
                Color.Transparent
            else
                Color(0xFFE2E8F0)
        )
    ) {

        Box(
            modifier = Modifier.padding(
                horizontal = 24.dp,
                vertical = 14.dp
            ),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = text,
                color = textColor,
                fontWeight = FontWeight.Bold
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

    val maxConsumo = historico.maxOf { it.consumo }
    val minConsumo = historico.minOf { it.consumo }
    val range = maxConsumo - minConsumo

    val animatedProgress by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(1800),
        label = "graphAnimation"
    )

    Column {

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
        ) {

            val canvasWidth = size.width
            val canvasHeight = size.height

            val spacing =
                canvasWidth / (historico.size - 1)

            val points = historico.mapIndexed { index, item ->

                val x = index * spacing

                val normalized =
                    (item.consumo - minConsumo) / range

                val y =
                    canvasHeight - (normalized * canvasHeight * 0.8f) - 40f

                Offset(x, y)
            }

            // GRID
            repeat(5) { i ->

                val y = (canvasHeight / 5) * i

                drawLine(
                    color = Color(0xFFE2E8F0),
                    start = Offset(0f, y),
                    end = Offset(canvasWidth, y),
                    strokeWidth = 1f
                )
            }

            val strokePath = Path()
            val fillPath = Path()

            points.forEachIndexed { index, point ->

                if (index == 0) {

                    strokePath.moveTo(point.x, point.y)
                    fillPath.moveTo(point.x, point.y)

                } else {

                    val previous = points[index - 1]

                    val controlX1 =
                        previous.x + (point.x - previous.x) / 2

                    val controlX2 = controlX1

                    strokePath.cubicTo(
                        controlX1,
                        previous.y,
                        controlX2,
                        point.y,
                        point.x,
                        point.y
                    )

                    fillPath.cubicTo(
                        controlX1,
                        previous.y,
                        controlX2,
                        point.y,
                        point.x,
                        point.y
                    )
                }
            }

            fillPath.lineTo(canvasWidth, canvasHeight)
            fillPath.lineTo(0f, canvasHeight)
            fillPath.close()

            // GRADIENT
            drawPath(
                path = fillPath,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF2196F3).copy(alpha = 0.35f),
                        Color.Transparent
                    )
                )
            )

            // GLOW
            drawPath(
                path = strokePath,
                color = Color(0xFF2196F3).copy(alpha = 0.15f),
                style = Stroke(width = 12f)
            )

            // LINE
            drawPath(
                path = strokePath,
                color = Color(0xFF2196F3),
                style = Stroke(
                    width = 5f,
                    cap = StrokeCap.Round
                ),
                alpha = animatedProgress
            )

            // POINTS
            points.forEach { point ->

                drawCircle(
                    color = Color.White,
                    radius = 10f,
                    center = point
                )

                drawCircle(
                    color = Color(0xFF2196F3),
                    radius = 6f,
                    center = point
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // LABELS
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            historico.forEach {

                Text(
                    text = it.label,
                    color = Color.Gray,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

// =========================
// BUILDING CHIP
// =========================

@Composable
private fun EdificioChip(
    edificio: Edificio,
    isSelected: Boolean,
    onClick: () -> Unit
) {

    val borderColor by animateColorAsState(
        if (isSelected)
            Color(0xFF2196F3)
        else
            Color(0xFFE2E8F0),
        label = ""
    )

    Card(
        modifier = Modifier
            .clickable { onClick() }
            .shadow(10.dp, RoundedCornerShape(22.dp)),

        shape = RoundedCornerShape(22.dp),

        border = BorderStroke(
            2.dp,
            borderColor
        ),

        colors = CardDefaults.cardColors(
            containerColor =
                if (isSelected)
                    Color(0xFFF0F7FF)
                else
                    Color.White
        )
    ) {

        Column(
            modifier = Modifier
                .width(90.dp)
                .padding(18.dp),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = edificio.id,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                color =
                    if (isSelected)
                        Color(0xFF2196F3)
                    else
                        Color.Gray
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "${edificio.consumoActual.roundToInt()}",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}

// =========================
// PREVIEW
// =========================

@Preview(showBackground = true)
@Composable
fun PreviewReports() {
    ReportsScreen()
}