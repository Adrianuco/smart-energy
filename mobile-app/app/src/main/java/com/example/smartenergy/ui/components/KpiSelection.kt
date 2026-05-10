package com.example.smartenergy.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MeetingRoom
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Update
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun KpiSection() {
    Column {
        Text(
            text = "Resumen del Semestre",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            KpiCard(
                modifier = Modifier.weight(1f),
                label = "Aulas con Horario",
                value = "42",
                icon = Icons.Default.MeetingRoom
            )
            KpiCard(
                modifier = Modifier.weight(1f),
                label = "Horas/Día",
                value = "128h",
                icon = Icons.Default.Timer
            )
        }
        Spacer(Modifier.height(12.dp))
        KpiCard(
            modifier = Modifier.fillMaxWidth(),
            label = "Última actualización",
            value = "10 May 2026, 08:30 AM",
            icon = Icons.Default.Update
        )
    }
}
