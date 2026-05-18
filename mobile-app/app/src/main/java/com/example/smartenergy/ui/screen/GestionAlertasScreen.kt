package com.example.smartenergy.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Error
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.smartenergy.ui.theme.AppColors

data class Alerta(
    val aula: String,
    val edificio: String,
    val tipo: String,
    val hora: String,
    val estado: String
)

val listaAlertas = listOf(
    Alerta("Aula B-104", "Edificio B", "Apagar AC", "2:30 PM", "Pendiente"),
    Alerta("Aula C-202", "Edificio C", "Revisar consumo", "3:15 PM", "Advertencia"),
    Alerta("Aula E-101", "Edificio E", "Apagar AC", "5:00 PM", "Pendiente"),
    Alerta("Aula A-104", "Edificio A", "Todo correcto", "1:00 PM", "Resuelta"),
    Alerta("Aula D-202", "Edificio D", "Encender AC", "7:45 AM", "Pendiente")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GestionAlertasScreen() {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            "Alertas",
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            "${listaAlertas.count { it.estado != "Resuelta" }} activas",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp),
            contentPadding = PaddingValues(top = 8.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(listaAlertas) { alerta ->
                AlertaCard(alerta)
            }
        }
    }
}

@Composable
private fun AlertaCard(alerta: Alerta) {
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
                    onClick = { },
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