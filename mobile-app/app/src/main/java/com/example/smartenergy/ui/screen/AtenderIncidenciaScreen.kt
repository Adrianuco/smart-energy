package com.example.smartenergy.ui.screen

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
import com.example.smartenergy.ui.components.atenderincidencia.EstadoChip
import com.example.smartenergy.ui.components.gestionincidencias.StatusBadge
import com.example.smartenergy.ui.theme.AppColors
import com.example.smartenergy.model.Incidencia
import com.example.smartenergy.model.EstadoIncidencia

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AtenderIncidenciaScreen(
    incidenciaId: String,
    onBack: () -> Unit
) {
    // Simulamos obtener la incidencia por ID
    val incidencia = listaIncidenciasMock.find { it.id == incidenciaId } ?: listaIncidenciasMock[0]
    
    var observaciones by remember { mutableStateOf("") }
    var nuevoEstado by remember { mutableStateOf(incidencia.estado) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Atender Incidencia") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
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
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Información General
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        StatusBadge(incidencia.estado)
                        Spacer(modifier = Modifier.weight(1f))
                        Text(incidencia.fecha, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    
                    Text(text = incidencia.tipo, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text(text = "${incidencia.aula?.codigo ?: ""} · ${incidencia.aula?.edificio?.nombre ?: ""}", style = MaterialTheme.typography.bodyMedium)
                    
                    HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
                    
                    Text(text = "Descripción del Reporte", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                    Text(text = incidencia.descripcion, style = MaterialTheme.typography.bodyMedium)
                }
            }

            // Gestión de la Incidencia
            Text("Gestión y Resolución", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Actualizar Estado", style = MaterialTheme.typography.labelMedium)
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    EstadoChip(
                        label = "En Revisión",
                        selected = nuevoEstado == EstadoIncidencia.EN_REVISION,
                        color = AppColors.StatusWarning
                    ) { nuevoEstado = EstadoIncidencia.EN_REVISION }
                    
                    EstadoChip(
                        label = "Resuelta",
                        selected = nuevoEstado == EstadoIncidencia.RESUELTA,
                        color = AppColors.StatusOk
                    ) { nuevoEstado = EstadoIncidencia.RESUELTA }
                }
            }

            OutlinedTextField(
                value = observaciones,
                onValueChange = { observaciones = it },
                label = { Text("Observaciones del técnico") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                shape = RoundedCornerShape(12.dp)
            )

            Button(
                onClick = { onBack() },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Guardar Cambios", fontWeight = FontWeight.Bold)
            }
            
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
