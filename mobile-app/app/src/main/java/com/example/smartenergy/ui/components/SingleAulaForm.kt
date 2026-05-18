package com.example.smartenergy.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.smartenergy.model.listaEquiposAC
import com.example.smartenergy.ui.screen.listaEdificios

@Composable
fun SingleAulaForm(initialBuildingName: String?) {
    var aulaNombre by remember { mutableStateOf("") }
    var floor by remember { mutableStateOf("1") }
    var selectedBuilding by remember { mutableStateOf(initialBuildingName ?: listaEdificios.firstOrNull()?.nombre ?: "") }
    var selectedAC by remember { mutableStateOf(listaEquiposAC.firstOrNull()) }

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
            value = aulaNombre,
            onValueChange = { aulaNombre = it },
            label = { Text("Nombre del Aula (ej: B-101)") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = textFieldColors
        )

        OutlinedTextField(
            value = floor,
            onValueChange = { floor = it },
            label = { Text("Piso") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            shape = RoundedCornerShape(12.dp),
            colors = textFieldColors
        )

        ACSelector(selectedAC) { selectedAC = it }

        Button(
            onClick = { /* TODO */ },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Icon(Icons.Outlined.Add, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text(
                "Registrar Aula Individual",
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}