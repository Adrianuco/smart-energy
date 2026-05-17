package com.example.smartenergy.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartenergy.model.Aula
import com.example.smartenergy.model.Edificio
import com.example.smartenergy.model.EquipoAC
import com.example.smartenergy.ui.components.EdificioCard
import com.example.smartenergy.ui.components.FilterBar

val listaAulasEdficioA = listOf(
    Aula("Aula A-101", 1, 60f, EquipoAC("AC-A1", "LG", "ArtCool", 12000, "SEER 18")),
    Aula("Aula A-104", 1, 80f, EquipoAC("AC-A2", "Samsung", "WindFree", 18000, "SEER 21")),
    Aula("Aula A-202", 2, 40f, EquipoAC("AC-A3", "Panasonic", "Inverter", 12000, "SEER 16")),
)
val listaAulasEdficioB = listOf(
    Aula("Aula B-101", 1, 60f, EquipoAC("AC-B1", "Carrier", "Optima", 24000, "SEER 14")),
    Aula("Aula B-104", 1, 80f, EquipoAC("AC-B2", "Daikin", "Fit", 18000, "SEER 17")),
    Aula("Aula B-202", 2, 40f, EquipoAC("AC-B3", "Trane", "XR14", 12000, "SEER 14")),
)
val listaAulasEdficioC = listOf(
    Aula("Aula C-101", 1, 60f, EquipoAC("AC-C1", "York", "LX Series", 12000, "SEER 16")),
    Aula("Aula C-104", 1, 80f, EquipoAC("AC-C2", "Lennox", "Elite", 18000, "SEER 18")),
    Aula("Aula C-202", 2, 40f, EquipoAC("AC-C3", "Midea", "Mission", 12000, "SEER 15")),
)
val listaAulasEdficioD = listOf(
    Aula("Aula D-101", 1, 60f, EquipoAC("AC-D1", "LG", "Dual Inverter", 12000, "SEER 19")),
    Aula("Aula D-104", 1, 80f, EquipoAC("AC-D2", "Samsung", "Digital Inverter", 18000, "SEER 20")),
    Aula("Aula D-202", 2, 40f, EquipoAC("AC-D3", "Panasonic", "Nanoe-G", 12000, "SEER 17")),
)
val listaAulasEdficioE = listOf(
    Aula("Aula E-101", 1, 60f, EquipoAC("AC-E1", "Carrier", "Comfort", 12000, "SEER 13")),
    Aula("Aula E-104", 1, 80f, EquipoAC("AC-E2", "Daikin", "SkyAir", 24000, "SEER 15")),
    Aula("Aula E-202", 2, 40f, EquipoAC("AC-E3", "York", "Affinity", 12000, "SEER 17")),
)
val listaEdificios = listOf(
    Edificio("Edificio A", 240f ,"OK", listaAulasEdficioA),
    Edificio("Edificio B", 240f, "Problemas", listaAulasEdficioB),
    Edificio("Edificio C", 240f, "Advertencias", listaAulasEdficioC),
    Edificio("Edificio D", 240f, "OK", listaAulasEdficioD),
    Edificio("Edificio E", 240f, "Problemas", listaAulasEdficioE),
)

@Composable
fun EdificiosScreen(
    onEdificioClick: (String) -> Unit,
    onAddEdificioClick: () -> Unit
) {

    var filtroSeleccionado by remember { mutableStateOf("Todos") }

    val edificiosFiltrados = if (filtroSeleccionado == "Todos") { listaEdificios }
        else { listaEdificios.filter { it.estado == filtroSeleccionado } }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 140.dp, start = 20.dp, end = 20.dp)
    ) {
        Text(text = "Edificios", fontSize = 50.sp)

        Spacer(modifier = Modifier.height(25.dp))

        Button(
            onClick = { onAddEdificioClick() },
            modifier = Modifier.align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(Icons.Default.AddCircle, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text("Agregar Edificio")
        }

        Spacer(modifier = Modifier.height(16.dp))

        FilterBar(
            seleccionado = filtroSeleccionado,
            onOptionSelected = { nuevoFiltro -> filtroSeleccionado = nuevoFiltro }
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(edificiosFiltrados) { edificio ->
                EdificioCard(edificio, onCardClick = { onEdificioClick(edificio.nombre) })
            }
        }
    }
}
