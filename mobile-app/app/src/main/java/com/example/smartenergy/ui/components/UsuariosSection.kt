package com.example.smartenergy.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.smartenergy.model.Rol
import com.example.smartenergy.model.listaUsuarios

@Composable
fun UsuariosSection() {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Personal Autorizado", style = MaterialTheme.typography.titleMedium)
            FloatingActionButton(onClick = { /* TODO */ }, modifier = Modifier.size(40.dp)) {
                Icon(Icons.Default.PersonAdd, contentDescription = null)
            }
        }
        Spacer(Modifier.height(16.dp))
        LazyColumn {
            items(listaUsuarios) { usuario ->
                ListItem(
                    headlineContent = { Text(usuario.nombre) },
                    supportingContent = { Text(usuario.email) },
                    trailingContent = {
                        AssistChip(
                            onClick = { },
                            label = { Text(usuario.rol.name) },
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = if (usuario.rol == Rol.ADMIN) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.secondaryContainer
                            )
                        )
                    },
                    leadingContent = { Icon(Icons.Default.Shield, contentDescription = null) }
                )
                HorizontalDivider()
            }
        }
    }
}