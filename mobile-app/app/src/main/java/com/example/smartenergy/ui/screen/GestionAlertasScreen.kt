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
import com.example.smartenergy.model.Alerta
import com.example.smartenergy.model.EstadoAlerta
import com.example.smartenergy.model.listaAulasEdficioA
import com.example.smartenergy.model.listaAulasEdficioB
import com.example.smartenergy.model.listaAulasEdficioC
import com.example.smartenergy.model.listaAulasEdficioD
import com.example.smartenergy.model.listaAulasEdficioE
import java.time.LocalDateTime

val listaAlertas = listOf(
    Alerta("1", "Apagar AC", EstadoAlerta.PENDIENTE, listaAulasEdficioB[1], LocalDateTime.now()), // Aula B-104
    Alerta("2", "Revisar consumo", EstadoAlerta.PENDIENTE, listaAulasEdficioC[2], LocalDateTime.now()), // Aula C-202
    Alerta("3", "Apagar AC", EstadoAlerta.PENDIENTE, listaAulasEdficioE[0], LocalDateTime.now()), // Aula E-101
    Alerta("4", "Todo correcto", EstadoAlerta.ATENDIDA, listaAulasEdficioA[1], LocalDateTime.now()), // Aula A-104
    Alerta("5", "Encender AC", EstadoAlerta.PENDIENTE, listaAulasEdficioD[2], LocalDateTime.now()) // Aula D-202
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
                            "${listaAlertas.count { it.estado != EstadoAlerta.ATENDIDA }} activas",
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
