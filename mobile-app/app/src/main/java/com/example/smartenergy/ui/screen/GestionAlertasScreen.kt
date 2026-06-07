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
import com.example.smartenergy.ui.components.gestionalertas.AlertaCard
import com.example.smartenergy.ui.theme.AppColors

data class Alerta(
    val id: String = "", // Added ID
    val aula: String,
    val edificio: String,
    val tipo: String,
    val hora: String,
    val estado: String
)

val listaAlertas = listOf(
    Alerta("1", "Aula B-104", "Edificio B", "Apagar AC", "2:30 PM", "Pendiente"),
    Alerta("2", "Aula C-202", "Edificio C", "Revisar consumo", "3:15 PM", "Advertencia"),
    Alerta("3", "Aula E-101", "Edificio E", "Apagar AC", "5:00 PM", "Pendiente"),
    Alerta("4", "Aula A-104", "Edificio A", "Todo correcto", "1:00 PM", "Resuelta"),
    Alerta("5", "Aula D-202", "Edificio D", "Encender AC", "7:45 AM", "Pendiente")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GestionAlertasScreen(
    onAtenderAlerta: (String) -> Unit = {}
) {

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
                AlertaCard(alerta, onAtenderClick = { onAtenderAlerta(alerta.id) })
            }
        }
    }
}
