package com.example.smartenergy.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.smartenergy.model.listaEquiposAC

@Composable
fun ACSection() {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Equipos Registrados", style = MaterialTheme.typography.titleMedium)
            Button(onClick = { /* TODO */ }) { Text("Añadir") }
        }
        Spacer(Modifier.height(16.dp))
        LazyColumn {
            items(listaEquiposAC) { equipo ->
                ListItem(
                    headlineContent = { Text("${equipo.marca} ${equipo.modelo}") },
                    supportingContent = { Text("${equipo.btu} BTU - Eficiencia: ${equipo.eficiencia}") },
                    leadingContent = { Icon(Icons.Default.AcUnit, contentDescription = null) }
                )
                HorizontalDivider()
            }
        }
    }
}