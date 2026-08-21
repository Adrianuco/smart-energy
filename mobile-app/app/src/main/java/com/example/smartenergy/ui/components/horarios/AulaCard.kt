package com.example.smartenergy.ui.components.horarios

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.example.smartenergy.model.Aula
import com.example.smartenergy.model.HorarioAcademico
import com.example.smartenergy.ui.theme.AppColors
import java.time.LocalTime

@Composable
fun AulaCard(aula: Aula, horarios: List<HorarioAcademico>) {
    val now = LocalTime.now()
    val todayOfWeek = java.time.LocalDate.now().dayOfWeek.value
    val aulaHorarios = horarios.filter { it.aula?.id == aula.id || it.aula?.codigo == aula.codigo }

    val currentClass = aulaHorarios.find {
        it.diaSemana == todayOfWeek && !now.isBefore(it.horaInicio) && !now.isAfter(it.horaFin)
    }

    val nextClass = aulaHorarios.filter {
        it.diaSemana == todayOfWeek && it.horaInicio.isAfter(now)
    }.minByOrNull { it.horaInicio }
        ?: aulaHorarios.filter { it.diaSemana > todayOfWeek }
            .minWithOrNull(compareBy<HorarioAcademico> { it.diaSemana }.thenBy { it.horaInicio })
        ?: aulaHorarios.minWithOrNull(compareBy<HorarioAcademico> { it.diaSemana }.thenBy { it.horaInicio })

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                text = "Aula ${aula.codigo}",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(Modifier.height(8.dp))
            HorizontalDivider(
                modifier = Modifier.alpha(0.4f),
                color = MaterialTheme.colorScheme.outlineVariant
            )
            Spacer(Modifier.height(8.dp))

            // Clase Actual
            if (currentClass != null) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        color = AppColors.TagCurrentBg,
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            "ACTUAL",
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            style = MaterialTheme.typography.labelSmall,
                            color = AppColors.TagCurrentText
                        )
                    }
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "${currentClass.asignatura} (${currentClass.horaInicio} - ${currentClass.horaFin})",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            } else {
                Text(
                    text = "No hay clase en curso",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            if (nextClass != null) {
                Spacer(Modifier.height(8.dp))

                // Clase Próxima
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        color = AppColors.TagNextBg,
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            "PRÓXIMA",
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            style = MaterialTheme.typography.labelSmall,
                            color = AppColors.TagNextText
                        )
                    }
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "${nextClass.asignatura} (${nextClass.horaInicio} - ${nextClass.horaFin})",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}