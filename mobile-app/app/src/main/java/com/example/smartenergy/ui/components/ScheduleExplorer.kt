package com.example.smartenergy.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.smartenergy.ui.components.AulaCard
import com.example.smartenergy.ui.components.EmptyExplorerState
import kotlin.collections.indexOf

@Composable
fun ScheduleExplorer() {
    var selectedBuilding by remember { mutableStateOf<String?>(null) }
    var searchQuery by remember { mutableStateOf("") }
    val buildings = listOf("Edificio A", "Edificio B", "Edificio C", "Edificio D")

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(
            text = "Auditoría en Tiempo Real",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )

        // Filtros por Edificio
        ScrollableTabRow(
            selectedTabIndex = buildings.indexOf(selectedBuilding).coerceAtLeast(0),
            edgePadding = 0.dp,
            containerColor = Color.Transparent,
            divider = {}
        ) {
            buildings.forEach { building ->
                FilterChip(
                    selected = selectedBuilding == building,
                    onClick = { selectedBuilding = if (selectedBuilding == building) null else building },
                    label = { Text(building) },
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
            }
        }

        // Barra de Búsqueda de Aula
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Buscar aula específica...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            singleLine = true,
            shape = MaterialTheme.shapes.medium
        )

        if (selectedBuilding == null && searchQuery.isEmpty()) {
            EmptyExplorerState()
        } else {
            // Lista de aulas (Mock)
            val classrooms = listOf("101", "102", "103", "104", "105", "201", "202")

            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                classrooms.forEach { classroomNumber ->
                    val fullRoomName = "${selectedBuilding?.last() ?: "B"}-$classroomNumber"
                    if (searchQuery.isEmpty() || fullRoomName.contains(searchQuery, ignoreCase = true)) {
                        AulaCard(fullRoomName)
                    }
                }
            }
        }
    }
}