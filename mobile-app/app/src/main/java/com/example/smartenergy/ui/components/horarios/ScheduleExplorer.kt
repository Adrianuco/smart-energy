package com.example.smartenergy.ui.components.horarios

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import com.example.smartenergy.model.HorarioAcademico

@Composable
fun ScheduleExplorer(
    horarios: List<HorarioAcademico>
) {
    // Extraer edificios dinámicamente o usar default si no hay asignados
    val buildings = horarios.mapNotNull { it.aula?.edificio?.nombre }.distinct().sorted()
        .ifEmpty { listOf("Edificio A", "Edificio B", "Edificio C", "Edificio D") }

    var selectedBuilding by remember(buildings) { mutableStateOf<String?>(buildings.firstOrNull()) }
    var searchQuery by remember { mutableStateOf("") }

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
            divider = {},
            indicator = {}
        ) {
            buildings.forEach { building ->
                FilterChip(
                    selected = selectedBuilding == building,
                    onClick = { selectedBuilding = if (selectedBuilding == building) null else building },
                    label = {
                        Text(
                            building,
                            style = MaterialTheme.typography.labelMedium
                        )
                    },
                    modifier = Modifier.padding(horizontal = 4.dp),
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        selectedLabelColor = MaterialTheme.colorScheme.primary
                    )
                )
            }
        }

        // Barra de Búsqueda de Aula
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    "Buscar aula específica...",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            leadingIcon = {
                Icon(
                    Icons.Outlined.Search,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
            )
        )

        if (selectedBuilding == null && searchQuery.isEmpty()) {
            EmptyExplorerState()
        } else {
            // Extraer aulas del edificio seleccionado
            val classrooms = horarios.mapNotNull { it.aula }
                .filter { selectedBuilding == null || it.edificio?.nombre == selectedBuilding }
                .distinctBy { it.id }

            val filteredClassrooms = if (searchQuery.isBlank()) {
                classrooms
            } else {
                classrooms.filter { it.codigo.contains(searchQuery, ignoreCase = true) }
            }

            if (filteredClassrooms.isEmpty()) {
                EmptyExplorerState()
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    filteredClassrooms.forEach { aula ->
                        AulaCard(aula = aula, horarios = horarios)
                    }
                }
            }
        }
    }
}