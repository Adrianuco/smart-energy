package com.example.smartenergy.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.smartenergy.model.EstadoAlerta
import com.example.smartenergy.model.Incidencia
import com.example.smartenergy.ui.components.gestionincidencias.IncidenciaItem
import com.example.smartenergy.viewmodel.incidencias.IncidenciasState
import com.example.smartenergy.viewmodel.incidencias.IncidenciasViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GestionIncidenciasScreen(
    onAtenderIncidencia: (String) -> Unit = {},
    viewModel: IncidenciasViewModel
) {
    var filtroEstado by remember { mutableStateOf<EstadoAlerta?>(null) }
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Gestión de Incidencias", style = MaterialTheme.typography.headlineSmall)
                        val subText = when (val currentState = state) {
                            is IncidenciasState.Success -> "${currentState.incidencias.size} reportes en total"
                            else -> "Cargando..."
                        }
                        Text(subText, style = MaterialTheme.typography.bodySmall)
                    }
                }
            )
        }
    ) { padding ->
        when (val currentState = state) {
            IncidenciasState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is IncidenciasState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Error: ${currentState.message}", color = MaterialTheme.colorScheme.error)
                }
            }
            is IncidenciasState.Success -> {
                Column(modifier = Modifier.padding(padding).padding(horizontal = 20.dp)) {
                    // Filtros rápidos
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        FilterChip(
                            selected = filtroEstado == null,
                            onClick = { filtroEstado = null },
                            label = { Text("Todas") }
                        )
                        FilterChip(
                            selected = filtroEstado == EstadoAlerta.PENDIENTE,
                            onClick = { filtroEstado = EstadoAlerta.PENDIENTE },
                            label = { Text("Pendientes") }
                        )
                        FilterChip(
                            selected = filtroEstado == EstadoAlerta.ATENDIDA,
                            onClick = { filtroEstado = EstadoAlerta.ATENDIDA },
                            label = { Text("Resueltas") }
                        )
                    }

                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(bottom = 20.dp)
                    ) {
                        val filtradas = if (filtroEstado == null) {
                            currentState.incidencias
                        } else {
                            currentState.incidencias.filter { it.estado == filtroEstado }
                        }
                        items(filtradas) { incidencia ->
                            IncidenciaItem(incidencia, onGestionarClick = { onAtenderIncidencia(incidencia.id ?: "") })
                        }
                    }
                }
            }
        }
    }
}
