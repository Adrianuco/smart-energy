package com.example.smartenergy.ui.components

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
import androidx.compose.ui.unit.dp
import com.example.smartenergy.model.EquipoAC
import com.example.smartenergy.model.listaEquiposAC

@Composable
fun ACSelector(selectedAC: EquipoAC?, onACSelected: (EquipoAC) -> Unit) {
    Column {
        Text(
            "Equipo AC a asignar:",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = androidx.compose.ui.Modifier.height(8.dp))
        listaEquiposAC.forEach { equipo ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = (selectedAC == equipo),
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