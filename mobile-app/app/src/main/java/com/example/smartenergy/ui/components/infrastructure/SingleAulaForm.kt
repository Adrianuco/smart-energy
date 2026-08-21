package com.example.smartenergy.ui.components.infrastructure

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.smartenergy.model.Aula
import com.example.smartenergy.model.Edificio
import com.example.smartenergy.model.Equipo
import com.example.smartenergy.viewmodel.infrastructure.InfrastructureViewModel

@Composable
fun SingleAulaForm(
    initialBuildingName: String?,
    viewModel: InfrastructureViewModel,
    equipos: List<Equipo>,
    edificios: List<Edificio>
) {
    var aulaNombre by remember { mutableStateOf("") }
    var floor by remember { mutableStateOf("1") }
    var selectedBuilding by remember { mutableStateOf(initialBuildingName ?: edificios.firstOrNull()?.nombre ?: "") }
    var selectedAC by remember { mutableStateOf(equipos.firstOrNull()) }
    var isSaving by remember { mutableStateOf(false) }

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

        ACSelector(selectedAC, equipos) { selectedAC = it }

        val saveState by viewModel.saveState.collectAsState()

        LaunchedEffect(saveState) {
            when (saveState) {
                is com.example.smartenergy.viewmodel.OperationState.Loading -> {
                    isSaving = true
                }
                is com.example.smartenergy.viewmodel.OperationState.Success -> {
                    isSaving = false
                    aulaNombre = ""
                    viewModel.resetSaveState()
                }
                is com.example.smartenergy.viewmodel.OperationState.Error -> {
                    isSaving = false
                    viewModel.resetSaveState()
                }
                else -> {}
            }
        }

        Button(
            onClick = {
                if (aulaNombre.isNotBlank()) {
                    val edificioObj = edificios.find { it.nombre == selectedBuilding }
                    val nuevoAula = Aula(
                        id = null,
                        codigo = aulaNombre,
                        piso = floor.toIntOrNull() ?: 1,
                        eficiencia = 100f,
                        equipo = selectedAC,
                        edificio = edificioObj
                    )
                    viewModel.guardarAula(nuevoAula)
                }
            },
            enabled = !isSaving && aulaNombre.isNotBlank(),
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