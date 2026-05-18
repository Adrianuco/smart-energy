package com.example.smartenergy.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.AutoMode
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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

    val textFieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = MaterialTheme.colorScheme.primary,
        unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
    )

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        OutlinedTextField(
            value = selectedBuilding,
            onValueChange = { selectedBuilding = it },
            label = { Text("Edificio") },
            modifier = Modifier.fillMaxWidth(),
            enabled = initialBuildingName == null,
            shape = RoundedCornerShape(12.dp),
            colors = textFieldColors
        )

        OutlinedTextField(
            value = prefix,
            onValueChange = { prefix = it },
            label = { Text("Prefijo (ej: B-)") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = textFieldColors
        )

        Text(
            "Pisos y Rangos",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onBackground
        )

        floorRanges.forEachIndexed { index, range ->
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                )
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        OutlinedTextField(
                            value = range.floor,
                            onValueChange = { floorRanges[index] = range.copy(floor = it) },
                            label = { Text("Piso") },
                            modifier = Modifier.weight(0.3f),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            shape = RoundedCornerShape(10.dp),
                            colors = textFieldColors
                        )
                        Spacer(Modifier.width(6.dp))
                        OutlinedTextField(
                            value = range.startRange,
                            onValueChange = { floorRanges[index] = range.copy(startRange = it) },
                            label = { Text("Desde") },
                            modifier = Modifier.weight(0.35f),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            shape = RoundedCornerShape(10.dp),
                            colors = textFieldColors
                        )
                        Spacer(Modifier.width(6.dp))
                        OutlinedTextField(
                            value = range.endRange,
                            onValueChange = { floorRanges[index] = range.copy(endRange = it) },
                            label = { Text("Hasta") },
                            modifier = Modifier.weight(0.35f),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            shape = RoundedCornerShape(10.dp),
                            colors = textFieldColors
                        )
                        if (floorRanges.size > 1) {
                            IconButton(onClick = { floorRanges.removeAt(index) }) {
                                Icon(
                                    Icons.Outlined.Delete,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.error
                                )
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
            Icon(
                Icons.Outlined.Add,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                "Añadir otro piso",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }

        ACSelector(selectedAC) { selectedAC = it }

        Button(
            onClick = { /* TODO: Lógica para iterar sobre floorRanges y generar aulas */ },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Icon(Icons.Outlined.AutoMode, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text(
                "Generar todas las aulas",
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}
