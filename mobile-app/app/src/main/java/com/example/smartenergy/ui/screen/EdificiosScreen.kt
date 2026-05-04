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
import com.example.smartenergy.model.Edificio
import com.example.smartenergy.ui.components.EdificioCard
import com.example.smartenergy.ui.components.FilterBar

@Composable
fun EdificiosScreen() {
    val listaEdificios = listOf(
        Edificio("Edificio A", "240 kWh" ,"OK"),
        Edificio("Edificio B", "240 kWh", "Problemas"),
        Edificio("Edificio C", "240 kWh", "Advertencias"),
        Edificio("Edificio D", "240 kWh","OK"),
        Edificio("Edificio E", "240 kWh", "Problemas"),
    )


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
            onClick = {  },
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
                EdificioCard(edificio, onCardClick = {})
            }
        }
    }
}
