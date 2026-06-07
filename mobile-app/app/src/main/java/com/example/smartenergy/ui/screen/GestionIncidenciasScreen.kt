package com.example.smartenergy.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.smartenergy.ui.components.gestionincidencias.IncidenciaItem
import com.example.smartenergy.ui.components.gestionincidencias.StatusBadge
import com.example.smartenergy.ui.theme.AppColors

data class Incidencia(
    val id: String,
    val aula: String,
    val edificio: String,
    val descripcion: String,
    val tipo: String,
    val fecha: String,
    val estado: EstadoIncidencia
)

enum class EstadoIncidencia {
    PENDIENTE, EN_REVISION, RESUELTA
}

val listaIncidenciasMock = listOf(
    Incidencia("1", "Aula B-104", "Edificio B", "El aire acondicionado hace un ruido extraño y no enfría bien.", "Falla Técnica", "12/10/2023", EstadoIncidencia.PENDIENTE),
    Incidencia("2", "Aula C-202", "Edificio C", "Ventana rota, se escapa el aire.", "Infraestructura", "11/10/2023", EstadoIncidencia.EN_REVISION),
    Incidencia("3", "Aula A-101", "Edificio A", "Consumo excesivo detectado fuera de horario.", "Desperdicio Energético", "10/10/2023", EstadoIncidencia.RESUELTA),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GestionIncidenciasScreen(
    onAtenderIncidencia: (String) -> Unit = {}
) {
    var filtroEstado by remember { mutableStateOf<EstadoIncidencia?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Gestión de Incidencias", style = MaterialTheme.typography.headlineSmall)
                        Text("${listaIncidenciasMock.size} reportes en total", style = MaterialTheme.typography.bodySmall)
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(horizontal = 20.dp)) {
            // Filtros rápidos
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = filtroEstado == null,
                    onClick = { filtroEstado = null },
                    label = { Text("Todas") }
                )
                FilterChip(
                    selected = filtroEstado == EstadoIncidencia.PENDIENTE,
                    onClick = { filtroEstado = EstadoIncidencia.PENDIENTE },
                    label = { Text("Pendientes") }
                )
                FilterChip(
                    selected = filtroEstado == EstadoIncidencia.RESUELTA,
                    onClick = { filtroEstado = EstadoIncidencia.RESUELTA },
                    label = { Text("Resueltas") }
                )
            }

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 20.dp)
            ) {
                val filtradas = if (filtroEstado == null) listaIncidenciasMock else listaIncidenciasMock.filter { it.estado == filtroEstado }
                items(filtradas) { incidencia ->
                    IncidenciaItem(incidencia, onGestionarClick = { onAtenderIncidencia(incidencia.id) })
                }
            }
        }
    }
}
