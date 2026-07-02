package com.example.smartenergy.ui.components.infrastructure

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.smartenergy.model.Equipo

@Composable
fun ACSelector(
    selectedAC: Equipo?,
    equipos: List<Equipo>,
    onACSelected: (Equipo) -> Unit
) {
    Column {
        Text(
            "Equipo AC a asignar:",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(8.dp))
        val distinctEquipos = equipos.distinctBy { it.modelo }
        distinctEquipos.forEach { equipo ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = (selectedAC?.modelo == equipo.modelo),
                    onClick = { onACSelected(equipo) },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colorScheme.primary
                    )
                )
                Text(
                    "${equipo.marca} ${equipo.modelo} (${equipo.btu} BTU)",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}