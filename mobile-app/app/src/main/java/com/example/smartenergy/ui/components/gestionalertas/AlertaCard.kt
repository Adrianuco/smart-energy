package com.example.smartenergy.ui.components.gestionalertas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Error
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.smartenergy.ui.screen.Alerta
import com.example.smartenergy.ui.theme.AppColors

@Composable
fun AlertaCard(alerta: Alerta, onAtenderClick: () -> Unit) {
    val statusConfig = getAlertStatusConfig(alerta.estado)

    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${alerta.aula} · ${alerta.edificio}",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = statusConfig.backgroundColor
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            statusConfig.icon,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp),
                            tint = statusConfig.color
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            alerta.estado,
                            style = MaterialTheme.typography.labelSmall,
                            color = statusConfig.color
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Column {
                    Text(
                        "Tipo",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        alerta.tipo,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                Column {
                    Text(
                        "Hora",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Outlined.Schedule,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            alerta.hora,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }

            if (alerta.estado != "Resuelta") {
                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = onAtenderClick,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        "Atender Alerta",
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }
    }
}

private data class AlertStatusConfig(
    val icon: ImageVector,
    val color: Color,
    val backgroundColor: Color
)

private fun getAlertStatusConfig(estado: String): AlertStatusConfig {
    return when (estado) {
        "Pendiente" -> AlertStatusConfig(
            icon = Icons.Outlined.Error,
            color = AppColors.StatusError,
            backgroundColor = AppColors.StatusErrorBackground
        )
        "Advertencia" -> AlertStatusConfig(
            icon = Icons.Outlined.Warning,
            color = AppColors.StatusWarning,
            backgroundColor = AppColors.StatusWarningBackground
        )
        "Resuelta" -> AlertStatusConfig(
            icon = Icons.Outlined.CheckCircle,
            color = AppColors.StatusOk,
            backgroundColor = AppColors.StatusOkBackground
        )
        else -> AlertStatusConfig(
            icon = Icons.Outlined.Error,
            color = AppColors.StatusError,
            backgroundColor = AppColors.StatusErrorBackground
        )
    }
}
