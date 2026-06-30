package com.example.smartenergy.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.smartenergy.ui.components.atenderalerta.DetailRow
import com.example.smartenergy.ui.theme.AppColors
import com.example.smartenergy.model.Alerta
import com.example.smartenergy.model.Estado
import com.example.smartenergy.viewmodel.OperationState

import com.example.smartenergy.viewmodel.alertas.AtenderAlertaState
import com.example.smartenergy.viewmodel.alertas.AtenderAlertaViewModel
import java.util.UUID.fromString

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AtenderAlertaScreen(
    alertaId: String,
    viewModel: AtenderAlertaViewModel,
    onBack: () -> Unit
) {
    LaunchedEffect(alertaId) {
        viewModel.findById(fromString(alertaId))
    }

    val state = viewModel.state.collectAsState()

    val estadoState by viewModel.estadoState.collectAsState()
    val atenderState by viewModel.atenderState.collectAsState()

    LaunchedEffect(estadoState) {
        if (estadoState is OperationState.Success) {
            viewModel.findById(fromString(alertaId))
            viewModel.resetEstadoState()
        }
    }

    LaunchedEffect(atenderState) {
        if (atenderState is OperationState.Success) {
            viewModel.resetAtenderState()
            onBack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Atender Alerta", style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
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
            AtenderAlertaState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is AtenderAlertaState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Error: ${currentState.message}")
                }
            }
            is AtenderAlertaState.Success -> {
                val alerta = currentState.alerta
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .verticalScroll(rememberScrollState())
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    // Cabecera de Estado Informativa
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f)
                        ),
                        elevation = CardDefaults.cardElevation(0.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Outlined.Info,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(32.dp)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text(
                                    text = "Acción Recomendada",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = "Verifique el estado del equipo en el aula y confirme la resolución.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }

                    // Detalles de la Alerta
                    Text(
                        "Información de la Alerta",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            DetailRow(icon = Icons.Outlined.Business, label = "Edificio", value = alerta.aula?.edificio?.nombre ?: "")
                            HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp), color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                            DetailRow(icon = Icons.Outlined.Room, label = "Aula", value = alerta.aula?.codigo ?: "")
                            HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp), color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                            DetailRow(icon = Icons.Outlined.NotificationsActive, label = "Tipo de Alerta", value = alerta.tipoAlerta)
                            HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp), color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                            DetailRow(icon = Icons.Outlined.Schedule, label = "Hora Detectada", value = alerta.fechaHora.toString())
                        }
                    }

                    val equipo = alerta.aula?.equipo
                    if (equipo != null) {
                        Text(
                            "Estado del Aire Acondicionado",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )

                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            elevation = CardDefaults.cardElevation(2.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                                DetailRow(icon = Icons.Outlined.DeviceThermostat, label = "Equipo", value = "${equipo.marca} ${equipo.modelo}")
                                HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp), color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                                
                                val isEncendido = equipo.estado == Estado.ENCENDIDO
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            Icons.Outlined.Air,
                                            contentDescription = null,
                                            tint = if (isEncendido) AppColors.StatusOk else MaterialTheme.colorScheme.onSurfaceVariant,
                                            modifier = Modifier.size(24.dp)
                                        )
                                        Spacer(modifier = Modifier.width(12.dp))
                                        Column {
                                            Text(
                                                "Estado actual",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                            Text(
                                                text = if (isEncendido) "ENCENDIDO" else "APAGADO",
                                                style = MaterialTheme.typography.bodyMedium,
                                                fontWeight = FontWeight.Bold,
                                                color = if (isEncendido) AppColors.StatusOk else MaterialTheme.colorScheme.onSurface
                                            )
                                        }
                                    }
                                    Switch(
                                        checked = isEncendido,
                                        onCheckedChange = { checked ->
                                            val nuevoEstado = if (checked) Estado.ENCENDIDO else Estado.APAGADO
                                            if (equipo.id != null) {
                                                viewModel.cambiarEstado(
                                                    fromString(equipo.id),
                                                    nuevoEstado
                                                )
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            viewModel.atenderAlerta(alerta)
                        },
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = AppColors.StatusOk)
                    ) {
                        Icon(Icons.Outlined.Check, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Confirmar Resuelta", fontWeight = FontWeight.Bold)
                    }

                    TextButton(
                        onClick = onBack,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Descartar", color = MaterialTheme.colorScheme.error)
                    }
                }
            }
        }
    }
}
