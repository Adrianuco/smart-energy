package com.example.smartenergy.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoMode
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.smartenergy.model.EquipoAC
import com.example.smartenergy.model.listaEquiposAC
import com.example.smartenergy.ui.screen.listaEdificios

data class FloorRange(
    val id: Int,
    var floor: String = "",
    var startRange: String = "",
    var endRange: String = ""
)

@Composable
fun DynamicAulaForm(initialBuildingName: String?) {
    var prefix by remember { mutableStateOf("B-") }
    var selectedBuilding by remember { mutableStateOf(initialBuildingName ?: listaEdificios.firstOrNull()?.nombre ?: "") }
    var selectedAC by remember { mutableStateOf(listaEquiposAC.firstOrNull()) }

    val floorRanges = remember { mutableStateListOf(FloorRange(id = 0, floor = "1", startRange = "101", endRange = "111")) }
    var nextId by remember { mutableStateOf(1) }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        OutlinedTextField(
            value = selectedBuilding,
            onValueChange = { selectedBuilding = it },
            label = { Text("Edificio") },
            modifier = Modifier.fillMaxWidth(),
            enabled = initialBuildingName == null
        )

        OutlinedTextField(value = prefix, onValueChange = { prefix = it }, label = { Text("Prefijo (ej: B-)") }, modifier = Modifier.fillMaxWidth())

        Text("Pisos y Rangos", style = MaterialTheme.typography.labelLarge)

        floorRanges.forEachIndexed { index, range ->
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        OutlinedTextField(
                            value = range.floor,
                            onValueChange = { floorRanges[index] = range.copy(floor = it) },
                            label = { Text("Piso") },
                            modifier = Modifier.weight(0.3f),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                        Spacer(Modifier.width(8.dp))
                        OutlinedTextField(
                            value = range.startRange,
                            onValueChange = { floorRanges[index] = range.copy(startRange = it) },
                            label = { Text("Desde") },
                            modifier = Modifier.weight(0.35f),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                        Spacer(Modifier.width(8.dp))
                        OutlinedTextField(
                            value = range.endRange,
                            onValueChange = { floorRanges[index] = range.copy(endRange = it) },
                            label = { Text("Hasta") },
                            modifier = Modifier.weight(0.35f),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                        if (floorRanges.size > 1) {
                            IconButton(onClick = { floorRanges.removeAt(index) }) {
                                Icon(Icons.Default.Delete, contentDescription = null, tint = MaterialTheme.colorScheme.error)
                            }
                        }
                    }
                }
            }
        }

        TextButton(
            onClick = {
                floorRanges.add(FloorRange(id = nextId++))
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Icon(Icons.Default.Add, contentDescription = null)
            Text("Añadir otro piso")
        }

        ACSelector(selectedAC) { selectedAC = it }

        Button(
            onClick = { /* TODO: Lógica para iterar sobre floorRanges y generar aulas */ },
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium
        ) {
            Icon(Icons.Default.AutoMode, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text("Generar todas las aulas")
        }
    }
}







