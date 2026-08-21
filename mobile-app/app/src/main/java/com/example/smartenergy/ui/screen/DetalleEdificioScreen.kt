package com.example.smartenergy.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Bolt
import androidx.compose.material.icons.outlined.ReportProblem
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import com.example.smartenergy.model.Edificio
import com.example.smartenergy.ui.theme.AppColors

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.*
import com.example.smartenergy.model.Aula
import com.example.smartenergy.model.Estado
import com.example.smartenergy.viewmodel.edificio.DetalleEdificioState
import com.example.smartenergy.viewmodel.edificio.DetalleEdificioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleEdificioScreen(
    edificioId: String?,
    edificioNombre: String,
    viewModel: DetalleEdificioViewModel,
    onAddAulasClick: (String) -> Unit,
    onGestionIncidenciasClick: () -> Unit
) {
    LaunchedEffect(edificioId) {
        viewModel.findDetalle(java.util.UUID.fromString(edificioId))
    }

    val state = viewModel.state.collectAsState()
    var selectedAula by remember { mutableStateOf<Aula?>(null) }
    val ahorroVal = when (val currentState = state.value) {
        is DetalleEdificioState.Success -> (currentState.detalleEdificio.ahorro * 100).toInt()
        else -> 0
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            edificioNombre,
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        val sizeText = when (val currentState = state.value) {
                            is DetalleEdificioState.Success -> "${currentState.detalleEdificio.aulas?.size ?: 0} aulas registradas"
                            else -> "Cargando..."
                        }
                        Text(
                            sizeText,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                actions = {
                    FilledTonalButton(
                        onClick = { onAddAulasClick(edificioNombre) },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.filledTonalButtonColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(Modifier.width(4.dp))
                        Text("Aulas", style = MaterialTheme.typography.labelLarge)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        when (val currentState = state.value) {
            DetalleEdificioState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is DetalleEdificioState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Error: ${currentState.message}", color = MaterialTheme.colorScheme.error)
                }
            }
            is DetalleEdificioState.Success -> {
                val edificio = currentState.detalleEdificio
                val consumo = edificio.consumo

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    Spacer(modifier = Modifier.height(4.dp))

                    // ── Savings Ring ──
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                "Ahorro respecto al Peor Escenario",
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            Box(
                                modifier = Modifier.size(160.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(
                                    progress = { ahorroVal / 100f },
                                    modifier = Modifier.fillMaxSize(),
                                    color = AppColors.StatusOk,
                                    strokeWidth = 10.dp,
                                    trackColor = MaterialTheme.colorScheme.outlineVariant,
                                    strokeCap = StrokeCap.Round,
                                )

                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = "$ahorroVal%",
                                        style = MaterialTheme.typography.headlineMedium,
                                        color = AppColors.StatusOk
                                    )
                                    Text(
                                        "Ahorrado",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }

                    // ── Incidencias Card (New approach instead of warnings) ──
                    Card(
                        onClick = onGestionIncidenciasClick,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.3f)),
                        elevation = CardDefaults.cardElevation(0.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(20.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Icon(
                                Icons.Outlined.ReportProblem,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.secondary,
                                modifier = Modifier.size(32.dp)
                            )
                            Column {
                                Text(
                                    "Gestionar Incidencias",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.onSecondaryContainer
                                )
                                Text(
                                    "Ver y resolver reportes de este edificio",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.7f)
                                )
                            }
                        }
                    }

                    // ── Stats Cards ──
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                modifier = Modifier.size(40.dp),
                                shape = CircleShape,
                                color = MaterialTheme.colorScheme.primaryContainer
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        Icons.Outlined.Bolt,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column {
                                Text(
                                    "Consumo Actual",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    "$consumo kWh",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
                        }
                    }

                    // ── Aulas List ──
                    Text(
                        "Listado de Aulas",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        edificio.aulas?.forEach { aula ->
                            Card(
                                onClick = { selectedAula = aula },
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                                elevation = CardDefaults.cardElevation(1.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        val dotColor = if (aula.equipo == null) AppColors.StatusError
                                                       else if (aula.equipo.estado == Estado.ENCENDIDO) AppColors.StatusOk
                                                       else AppColors.StatusWarning
                                        Surface(
                                            modifier = Modifier.size(8.dp),
                                            shape = CircleShape,
                                            color = dotColor
                                        ) {}
                                        Spacer(modifier = Modifier.width(12.dp))
                                        Text(
                                            aula.codigo,
                                            style = MaterialTheme.typography.bodyLarge,
                                            color = MaterialTheme.colorScheme.onBackground
                                        )
                                    }
                                    val statusText = if (aula.equipo == null) "Sin equipo"
                                                     else "Aire: ${aula.equipo.estado?.name ?: "APAGADO"}"
                                    Text(
                                        statusText,
                                        style = MaterialTheme.typography.titleSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }

    selectedAula?.let { aula ->
        AlertDialog(
            onDismissRequest = { selectedAula = null },
            title = {
                Text(
                    text = "Detalle de ${aula.codigo}",
                    style = MaterialTheme.typography.headlineSmall
                )
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Información del Aula",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text("Piso: ${aula.piso}")
                    Text("Eficiencia: ${aula.eficiencia}%")

                    Spacer(modifier = Modifier.height(8.dp))

                    val equipo = aula.equipo
                    if (equipo != null) {
                        Text(
                            text = "Aire Acondicionado",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text("Marca: ${equipo.marca}")
                        Text("Modelo: ${equipo.modelo}")
                        Text("BTU: ${equipo.btu}")
                        Text("Potencia Nominal: ${equipo.potenciaNominal} W")
                        
                        val isEncendido = equipo.estado == Estado.ENCENDIDO
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                        ) {
                            Text(
                                text = if (isEncendido) "Estado: ENCENDIDO" else "Estado: APAGADO",
                                style = MaterialTheme.typography.bodyLarge,
                                color = if (isEncendido) AppColors.StatusOk else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Switch(
                                checked = isEncendido,
                                onCheckedChange = { checked ->
                                    val nuevoEstado = if (checked) Estado.ENCENDIDO else Estado.APAGADO
                                    if (equipo.id != null && edificioId != null) {
                                        viewModel.cambiarEstado(
                                            java.util.UUID.fromString(equipo.id),
                                            nuevoEstado,
                                            java.util.UUID.fromString(edificioId)
                                        )
                                        selectedAula = null
                                    }
                                }
                            )
                        }
                    } else {
                        Text(
                            text = "No hay aire acondicionado registrado para esta aula.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { selectedAula = null }) {
                    Text("Cerrar")
                }
            }
        )
    }
}
