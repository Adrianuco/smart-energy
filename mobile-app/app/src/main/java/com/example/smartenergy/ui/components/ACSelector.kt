package com.example.smartenergy.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.example.smartenergy.model.EquipoAC
import com.example.smartenergy.model.listaEquiposAC

@Composable
fun ACSelector(selectedAC: EquipoAC?, onACSelected: (EquipoAC) -> Unit) {
    Column {
        Text("Equipo AC a asignar:", style = MaterialTheme.typography.bodyMedium)
        listaEquiposAC.forEach { equipo ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(selected = (selectedAC == equipo), onClick = { onACSelected(equipo) })
                Text("${equipo.marca} ${equipo.modelo} (${equipo.btu} BTU)")
            }
        }
    }
}