package com.example.smartenergy.ui.screen

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    val consumoPeorEscenario: Float, // AC encendido todo el tiempo de clase
    val ahorroLogrado: Float,
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
    EdificioReport("A", "Edificio A", 285f, 450f, 165f, 12.5f),
    EdificioReport("B", "Edificio B", 365f, 520f, 155f, 8.2f),
    EdificioReport("C", "Edificio C", 325f, 480f, 155f, -3.1f),
    EdificioReport("D", "Edificio D", 245f, 400f, 155f, 15.7f),
    EdificioReport("E", "Edificio E", 195f, 350f, 155f, -2.4f)
)

fun getHistorico(
    edificioId: String,
    periodo: String
): List<ConsumoHistorico> {
    val baseData = when (edificioId) {
        "A" -> listOf(120f, 180f, 220f, 190f, 250f, 210f, 260f)
        "B" -> listOf(200f, 240f, 280f, 260f, 320f, 290f, 340f)
        else -> listOf(100f, 140f, 180f, 160f, 200f, 190f, 220f)
    }

    return when (periodo) {
        "Semana" -> baseData.mapIndexed { index, value -> ConsumoHistorico(listOf("L", "M", "X", "J", "V", "S", "D")[index], value) }
        else -> List(4) { index -> ConsumoHistorico("S${index + 1}", baseData[index % baseData.size] * 1.3f) }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportsScreen() {
    var edificioSeleccionado by remember { mutableStateOf(edificiosReport[0]) }
    var periodoSeleccionado by remember { mutableStateOf("Semana") }
    var fechaSeleccionada by remember { mutableStateOf(LocalDate.now()) }
    var showDatePicker by remember { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState()

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let {
                        fechaSeleccionada = java.time.Instant.ofEpochMilli(it)
                            .atZone(java.time.ZoneId.systemDefault()).toLocalDate()
                    }
                    showDatePicker = false
                }) { Text("OK") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Reportes de Ahorro",
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable { showDatePicker = true }
                        ) {
                            Icon(Icons.Default.CalendarToday, contentDescription = null, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = fechaSeleccionada.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(text = "Seleccionar edificio", style = MaterialTheme.typography.titleSmall)

            LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                items(edificiosReport) { edificio ->
                    EdificioChip(edificio = edificio, isSelected = edificio == edificioSeleccionado) {
                        edificioSeleccionado = edificio
                    }
                }
            }

            CardAhorro(edificioSeleccionado)

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                TiempoChip("Hoy", periodoSeleccionado == "Hoy") { periodoSeleccionado = "Hoy" }
                TiempoChip("Semana", periodoSeleccionado == "Semana") { periodoSeleccionado = "Semana" }
                TiempoChip("Mes", periodoSeleccionado == "Mes") { periodoSeleccionado = "Mes" }
            }

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(text = "Histórico de Consumo", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(20.dp))
                    GraficoLineasHistorico(getHistorico(edificioSeleccionado.id, periodoSeleccionado))
                }
            }
        }
    }
}

@Composable
private fun CardAhorro(edificio: EdificioReport) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Text(text = edificio.nombre, style = MaterialTheme.typography.titleLarge)
                    Text(
                        text = "Ahorro: ${edificio.ahorroLogrado.roundToInt()} kWh",
                        style = MaterialTheme.typography.headlineMedium,
                        color = AppColors.StatusOk
                    )
                }
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = AppColors.StatusOkBackground
                ) {
                    Text(
                        text = "Eficiente",
                        color = AppColors.StatusOk,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }

            Divider()

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                MiniStat(Modifier.weight(1f), "Consumo Real", "${edificio.consumoActual.toInt()}", MaterialTheme.colorScheme.primary)
                MiniStat(Modifier.weight(1f), "Peor Escenario", "${edificio.consumoPeorEscenario.toInt()}", AppColors.StatusError)
                MiniStat(Modifier.weight(1f), "Ahorro %", "${((edificio.ahorroLogrado/edificio.consumoPeorEscenario)*100).toInt()}%", AppColors.StatusOk)
            }
        }
    }
}

@Composable
private fun MiniStat(modifier: Modifier, label: String, value: String, color: androidx.compose.ui.graphics.Color) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = value, style = MaterialTheme.typography.titleMedium, color = color)
        Text(text = label, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
private fun TiempoChip(text: String, selected: Boolean, onClick: () -> Unit) {
    val backgroundColor by animateColorAsState(if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface)
    val textColor by animateColorAsState(if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant)

    Card(
        modifier = Modifier.clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        border = if (!selected) BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant) else null
    ) {
        Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
            Text(text = text, color = textColor, style = MaterialTheme.typography.labelLarge)
        }
    }
}

@Composable
private fun GraficoLineasHistorico(historico: List<ConsumoHistorico>) {
    com.example.smartenergy.ui.components.SmartEnergyLineChart(
        data = historico.map { it.consumo },
        bottomLabels = historico.map { it.label },
        chartHeight = 200.dp,
        showAxis = true
    )
}

@Composable
private fun EdificioChip(edificio: EdificioReport, isSelected: Boolean, onClick: () -> Unit) {
    Card(
        modifier = Modifier.clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(if (isSelected) 2.dp else 1.dp, if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant),
        colors = CardDefaults.cardColors(containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f) else MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.width(80.dp).padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = edificio.id, style = MaterialTheme.typography.titleMedium)
        }
    }
}
