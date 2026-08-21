package com.example.smartenergy.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.smartenergy.ui.components.settings.*
import com.example.smartenergy.viewmodel.settings.SettingsViewModel
import com.example.smartenergy.viewmodel.usuarios.UsuariosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onAddUserClick: () -> Unit = {},
    settingsViewModel: SettingsViewModel,
    usuariosViewModel: UsuariosViewModel
) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Configuración", "Usuarios")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Ajustes del Sistema",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary
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
                0 -> {
                    GeneralSettings(viewModel = settingsViewModel)
                }
                1 -> {
                    UsuariosSection(onAddUserClick = onAddUserClick, viewModel = usuariosViewModel)
                }
            }
        }
    }
}
