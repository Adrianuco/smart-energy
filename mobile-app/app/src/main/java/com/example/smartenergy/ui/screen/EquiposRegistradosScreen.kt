package com.example.smartenergy.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.AcUnit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.smartenergy.model.listaEquipos
import com.example.smartenergy.model.listaEdificios
import com.example.smartenergy.ui.components.equiposregistrados.ACModelCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EquiposRegistradosScreen(
    onAddACClick: () -> Unit,
    onBack: () -> Unit
) {
    // Calcular unidades por modelo
    val todasLasAulas = listaEdificios.flatMap { it.aulas }
    val conteoUnidades = todasLasAulas.mapNotNull { it.equipo }
        .groupingBy { it.modelo }
        .eachCount()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Equipos Registrados") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddACClick,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Registrar Equipo")
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 80.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(listaEquipos) { equipo ->
                val unidades = conteoUnidades[equipo.modelo] ?: 0
                ACModelCard(equipo.marca, equipo.modelo, unidades, equipo.btu, equipo.eficiencia)
            }
        }
    }
}
