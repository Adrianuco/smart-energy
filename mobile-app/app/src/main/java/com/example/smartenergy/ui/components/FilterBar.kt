package com.example.smartenergy.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Error
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun FilterBar(seleccionado: String, onOptionSelected: (String) -> Unit) {
    val opciones = listOf(
        FilterOption("Todos", Icons.Outlined.FilterList),
        FilterOption("Problemas", Icons.Outlined.Error),
        FilterOption("Advertencias", Icons.Outlined.Warning),
        FilterOption("OK", Icons.Outlined.CheckCircle)
    )

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(opciones) { opcion ->
            val esSeleccionado = seleccionado == opcion.label
            Surface(
                modifier = Modifier.clickable { onOptionSelected(opcion.label) },
                shape = RoundedCornerShape(12.dp),
                color = if (esSeleccionado)
                    MaterialTheme.colorScheme.primaryContainer
                else
                    MaterialTheme.colorScheme.surface,
                border = BorderStroke(
                    1.dp,
                    if (esSeleccionado)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.outlineVariant
                )
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        opcion.icon,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = if (esSeleccionado)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(Modifier.width(6.dp))
                    Text(
                        text = opcion.label,
                        style = MaterialTheme.typography.labelMedium,
                        color = if (esSeleccionado)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

private data class FilterOption(
    val label: String,
    val icon: ImageVector
)