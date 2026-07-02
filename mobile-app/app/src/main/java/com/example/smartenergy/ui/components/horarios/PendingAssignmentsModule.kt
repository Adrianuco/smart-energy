package com.example.smartenergy.ui.components.horarios

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.outlined.AcUnit
import androidx.compose.material.icons.outlined.Assignment
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.smartenergy.model.Aula
import com.example.smartenergy.model.Equipo
import com.example.smartenergy.ui.theme.AppColors
import com.example.smartenergy.viewmodel.horarios.HorariosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PendingAssignmentsModule(
    aulasSinEquipo: List<Aula>,
    equiposDisponibles: List<Equipo>,
    viewModel: HorariosViewModel
) {
    val selectedAulas = remember { mutableStateListOf<Aula>() }
    var selectedEquipo by remember { mutableStateOf<Equipo?>(null) }
    var dropdownExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        Icons.Outlined.Assignment,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(8.dp)
                    )
                }
                Spacer(Modifier.width(16.dp))
                Column {
                    Text(
                        "Asignación de Equipos",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        "Asigne aires acondicionados a las aulas creadas",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            if (aulasSinEquipo.isEmpty()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                ) {
                    Icon(
                        Icons.Outlined.CheckCircle,
                        contentDescription = null,
                        tint = AppColors.StatusOk,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        "Todas las aulas tienen equipos asignados.",
                        color = AppColors.StatusOk,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            } else {
                Text(
                    "Aulas sin equipo asignado:",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TextButton(
                        onClick = {
                            selectedAulas.clear()
                            selectedAulas.addAll(aulasSinEquipo)
                        },
                        contentPadding = PaddingValues(horizontal = 4.dp, vertical = 2.dp)
                    ) {
                        Text("Seleccionar todas", style = MaterialTheme.typography.bodySmall)
                    }
                    TextButton(
                        onClick = {
                            selectedAulas.clear()
                        },
                        contentPadding = PaddingValues(horizontal = 4.dp, vertical = 2.dp)
                    ) {
                        Text("Deseleccionar todas", style = MaterialTheme.typography.bodySmall)
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 160.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Column {
                        aulasSinEquipo.forEach { aula ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        if (selectedAulas.contains(aula)) {
                                            selectedAulas.remove(aula)
                                        } else {
                                            selectedAulas.add(aula)
                                        }
                                    }
                                    .padding(vertical = 4.dp)
                            ) {
                                Checkbox(
                                    checked = selectedAulas.contains(aula),
                                    onCheckedChange = { isChecked ->
                                        if (isChecked == true) {
                                            selectedAulas.add(aula)
                                        } else {
                                            selectedAulas.remove(aula)
                                        }
                                    }
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(
                                        text = aula.codigo,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onBackground
                                    )
                                    Text(
                                        text = aula.edificio?.nombre ?: "Sin Edificio",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))

                ExposedDropdownMenuBox(
                    expanded = dropdownExpanded,
                    onExpandedChange = { dropdownExpanded = !dropdownExpanded },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = selectedEquipo?.let { "${it.marca} ${it.modelo} (${it.btu} BTU)" } ?: "Seleccionar Equipo AC",
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(),
                        trailingIcon = {
                            Icon(
                                Icons.Default.ArrowDropDown,
                                contentDescription = null
                            )
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
                        )
                    )
                    ExposedDropdownMenu(
                        expanded = dropdownExpanded,
                        onDismissRequest = { dropdownExpanded = false }
                    ) {
                        val distinctEquipos = equiposDisponibles.distinctBy { it.modelo }
                        distinctEquipos.forEach { equipo ->
                            DropdownMenuItem(
                                text = {
                                    Text("${equipo.marca} ${equipo.modelo} (${equipo.btu} BTU)")
                                },
                                onClick = {
                                    selectedEquipo = equipo
                                    dropdownExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))

                Button(
                    onClick = {
                        selectedEquipo?.let { equipo ->
                            viewModel.asignarEquipo(selectedAulas.toList(), equipo)
                            selectedAulas.clear()
                            selectedEquipo = null
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    enabled = selectedAulas.isNotEmpty() && selectedEquipo != null,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(Icons.Outlined.AcUnit, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Asignar Equipo Seleccionado",
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }
    }
}
