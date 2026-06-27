package com.example.smartenergy.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.smartenergy.ui.components.horarios.ImportModule
import com.example.smartenergy.ui.components.horarios.KpiSection
import com.example.smartenergy.ui.components.horarios.ScheduleExplorer
import com.example.smartenergy.ui.components.horarios.PendingAssignmentsModule

import androidx.compose.ui.Alignment
import androidx.compose.runtime.collectAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import com.example.smartenergy.viewmodel.horarios.HorariosState
import com.example.smartenergy.viewmodel.horarios.HorariosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HorariosScreen(
    viewModel: HorariosViewModel
) {
    val state by viewModel.state.collectAsState()
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            "Programación Académica",
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            "Gestión de horarios del semestre",
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
        when (val currentState = state) {
            HorariosState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is HorariosState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Error: ${currentState.message}", color = MaterialTheme.colorScheme.error)
                }
            }
            is HorariosState.Success -> {
                Column(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .padding(horizontal = 20.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    // 1. Resumen Estadístico (KPIs)
                    KpiSection(horarios = currentState.horarios)

                    // 2. Módulo de Carga Excel
                    ImportModule(viewModel = viewModel)

                    // 2.5 Módulo de asignación de equipos para aulas pendientes
                    PendingAssignmentsModule(
                        aulasSinEquipo = currentState.aulasSinEquipo,
                        equiposDisponibles = currentState.equiposDisponibles,
                        viewModel = viewModel
                    )

                    // 3. Explorador de Horarios Real-Time
                    ScheduleExplorer(horarios = currentState.horarios)

                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}
