package com.example.smartenergy.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.smartenergy.ui.components.infrastructure.ACSection
import com.example.smartenergy.ui.components.infrastructure.AulaSection
import com.example.smartenergy.ui.components.infrastructure.EdificiosSection
import com.example.smartenergy.viewmodel.infrastructure.InfrastructureState
import com.example.smartenergy.viewmodel.infrastructure.InfrastructureViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfrastructureScreen(
    initialBuildingName: String? = null,
    onAddACClick: () -> Unit = {},
    onBack: () -> Unit,
    viewModel: InfrastructureViewModel
) {
    val state by viewModel.state.collectAsState()
    var selectedTab by remember { mutableStateOf(if (initialBuildingName != null) 0 else 2) }
    val tabs = listOf("Aulas", "Equipos AC", "Edificios")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Gestión de Infraestructura",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = MaterialTheme.colorScheme.onBackground
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
            InfrastructureState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is InfrastructureState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = currentState.message,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            is InfrastructureState.Success -> {
                Column(modifier = Modifier.padding(padding)) {
                    ScrollableTabRow(
                        selectedTabIndex = selectedTab,
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.primary,
                        edgePadding = 16.dp
                    ) {
                        tabs.forEachIndexed { index, title ->
                            Tab(
                                selected = selectedTab == index,
                                onClick = { selectedTab = index },
                                text = {
                                    Text(
                                        title,
                                        style = MaterialTheme.typography.labelLarge,
                                        color = if (selectedTab == index)
                                            MaterialTheme.colorScheme.primary
                                        else
                                            MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            )
                        }
                    }

                    when (selectedTab) {
                        0 -> AulaSection(
                            initialBuildingName = initialBuildingName,
                            viewModel = viewModel,
                            equipos = currentState.equipos,
                            edificios = currentState.edificios
                        )
                        1 -> ACSection(
                            onAddACClick = onAddACClick,
                            equipos = currentState.equipos
                        )
                        2 -> EdificiosSection(
                            viewModel = viewModel,
                            edificios = currentState.edificios
                        )
                    }
                }
            }
        }
    }
}
