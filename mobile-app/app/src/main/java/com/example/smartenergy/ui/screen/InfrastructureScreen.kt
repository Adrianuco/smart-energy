package com.example.smartenergy.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.smartenergy.model.EquipoAC
import com.example.smartenergy.model.listaEquiposAC
import com.example.smartenergy.ui.components.ACSection
import com.example.smartenergy.ui.components.AulaSection
import com.example.smartenergy.ui.components.DynamicAulaForm
import com.example.smartenergy.ui.components.EdificiosSection
import com.example.smartenergy.ui.components.SingleAulaForm

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfrastructureScreen(
    initialBuildingName: String? = null,
    onBack: () -> Unit
) {
    var selectedTab by remember { mutableStateOf(if (initialBuildingName != null) 0 else 2) }
    val tabs = listOf("Aulas", "Equipos AC", "Edificios")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Gestión de Infraestructura") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            ScrollableTabRow(selectedTabIndex = selectedTab) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title) }
                    )
                }
            }

            when (selectedTab) {
                0 -> AulaSection(initialBuildingName)
                1 -> ACSection()
                2 -> EdificiosSection()
            }
        }
    }
}




