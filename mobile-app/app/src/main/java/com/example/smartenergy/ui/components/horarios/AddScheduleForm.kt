package com.example.smartenergy.ui.components.horarios

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.smartenergy.model.Aula
import com.example.smartenergy.model.HorarioAcademico
import com.example.smartenergy.viewmodel.horarios.HorariosViewModel
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScheduleForm(
    todasAulas: List<Aula>,
    viewModel: HorariosViewModel
) {
    var showDialog by remember { mutableStateOf(false) }

    Button(
        onClick = { showDialog = true },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Icon(Icons.Default.Add, contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        Text("Agregar Horario Manual")
    }

    if (showDialog) {
        var asignatura by remember { mutableStateOf("") }
        var diaSeleccionado by remember { mutableIntStateOf(1) } // 1 = Lunes
        var horaInicioStr by remember { mutableStateOf("08:00") }
        var horaFinStr by remember { mutableStateOf("09:40") }
        var aulaSeleccionada by remember { mutableStateOf<Aula?>(todasAulas.firstOrNull()) }
        var errorMsg by remember { mutableStateOf("") }

        var aulaMenuExpanded by remember { mutableStateOf(false) }
        var diaMenuExpanded by remember { mutableStateOf(false) }

        val diasSemana = listOf(
            "Lunes" to 1,
            "Martes" to 2,
            "Miércoles" to 3,
            "Jueves" to 4,
            "Viernes" to 5,
            "Sábado" to 6,
            "Domingo" to 7
        )

        Dialog(onDismissRequest = { showDialog = false }) {
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Nuevo Horario Académico",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                    )

                    OutlinedTextField(
                        value = asignatura,
                        onValueChange = { asignatura = it },
                        label = { Text("Asignatura / Clase") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Día de la semana Dropdown
                    Box(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = diasSemana.firstOrNull { it.second == diaSeleccionado }?.first ?: "Lunes",
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Día de la Semana") },
                            trailingIcon = {
                                Icon(
                                    Icons.Default.ArrowDropDown,
                                    contentDescription = null,
                                    modifier = Modifier.clickable { diaMenuExpanded = true }
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { diaMenuExpanded = true }
                        )
                        DropdownMenu(
                            expanded = diaMenuExpanded,
                            onDismissRequest = { diaMenuExpanded = false }
                        ) {
                            diasSemana.forEach { (nombre, valor) ->
                                DropdownMenuItem(
                                    text = { Text(nombre) },
                                    onClick = {
                                        diaSeleccionado = valor
                                        diaMenuExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    // Hora Inicio y Fin
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = horaInicioStr,
                            onValueChange = { horaInicioStr = it },
                            label = { Text("Inicio (HH:mm)") },
                            modifier = Modifier.weight(1f),
                            placeholder = { Text("08:00") }
                        )
                        OutlinedTextField(
                            value = horaFinStr,
                            onValueChange = { horaFinStr = it },
                            label = { Text("Fin (HH:mm)") },
                            modifier = Modifier.weight(1f),
                            placeholder = { Text("09:40") }
                        )
                    }

                    // Aula Dropdown
                    Box(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = aulaSeleccionada?.codigo ?: "Seleccionar Aula",
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Aula") },
                            trailingIcon = {
                                Icon(
                                    Icons.Default.ArrowDropDown,
                                    contentDescription = null,
                                    modifier = Modifier.clickable { aulaMenuExpanded = true }
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { aulaMenuExpanded = true }
                        )
                        DropdownMenu(
                            expanded = aulaMenuExpanded,
                            onDismissRequest = { aulaMenuExpanded = false }
                        ) {
                            todasAulas.forEach { aula ->
                                DropdownMenuItem(
                                    text = { Text(aula.codigo ?: "") },
                                    onClick = {
                                        aulaSeleccionada = aula
                                        aulaMenuExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    if (errorMsg.isNotEmpty()) {
                        Text(
                            text = errorMsg,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(onClick = { showDialog = false }) {
                            Text("Cancelar")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = {
                                if (asignatura.isBlank()) {
                                    errorMsg = "La asignatura no puede estar vacía"
                                    return@Button
                                }
                                val hi = try {
                                    LocalTime.parse(horaInicioStr.trim())
                                } catch (e: Exception) {
                                    errorMsg = "Hora de inicio inválida (use HH:mm)"
                                    return@Button
                                }
                                val hf = try {
                                    LocalTime.parse(horaFinStr.trim())
                                } catch (e: Exception) {
                                    errorMsg = "Hora de fin inválida (use HH:mm)"
                                    return@Button
                                }
                                if (hf.isBefore(hi)) {
                                    errorMsg = "La hora de fin debe ser posterior a la de inicio"
                                    return@Button
                                }
                                if (aulaSeleccionada == null) {
                                    errorMsg = "Debe seleccionar un aula"
                                    return@Button
                                }

                                val nuevoHorario = HorarioAcademico(
                                    asignatura = asignatura,
                                    diaSemana = diaSeleccionado,
                                    horaInicio = hi,
                                    horaFin = hf,
                                    aula = aulaSeleccionada
                                )

                                viewModel.crearHorario(nuevoHorario)
                                showDialog = false
                            }
                        ) {
                            Text("Guardar")
                        }
                    }
                }
            }
        }
    }
}
