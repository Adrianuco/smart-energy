package com.example.smartenergy.ui.components.horarios

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MeetingRoom
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Update
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.smartenergy.model.HorarioAcademico

@Composable
fun KpiSection(horarios: List<HorarioAcademico>) {
    val uniqueAulas = horarios.mapNotNull { it.aula?.codigo }.distinct().size
    val totalMinutes = horarios.sumOf {
        java.time.Duration.between(it.horaInicio, it.horaFin).toMinutes()
    }
    val totalHours = totalMinutes / 60.0
    val formattedHours = if (totalHours % 1.0 == 0.0) "${totalHours.toInt()}h" else String.format(java.util.Locale.US, "%.1fh", totalHours)

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
                value = uniqueAulas.toString(),
                icon = Icons.Outlined.MeetingRoom
            )
            KpiCard(
                modifier = Modifier.weight(1f),
                label = "Horas Programadas",
                value = formattedHours,
                icon = Icons.Outlined.Schedule
            )
        }
        Spacer(Modifier.height(12.dp))
        KpiCard(
            modifier = Modifier.fillMaxWidth(),
            label = "Última actualización",
            value = if (horarios.isNotEmpty()) "Recientemente actualizado" else "Sin horarios programados",
            icon = Icons.Outlined.Update
        )
    }
}
