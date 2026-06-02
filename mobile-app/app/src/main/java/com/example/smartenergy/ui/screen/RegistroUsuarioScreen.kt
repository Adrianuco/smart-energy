package com.example.smartenergy.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartenergy.model.Rol

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroUsuarioScreen(
    onRegistroSuccess: () -> Unit = {}
) {
    var id by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var cifra by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rol by remember { mutableStateOf(Rol.APOYO_LOGISTICO) }
    var activo by remember { mutableStateOf(true) }
    var nivelAcceso by remember { mutableStateOf("") } // Solo para Administrador

    var expandedRol by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registrar Usuario") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Datos Personales",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            OutlinedTextField(
                value = id,
                onValueChange = { id = it },
                label = { Text("ID / Cédula") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp)
                )
                OutlinedTextField(
                    value = apellido,
                    onValueChange = { apellido = it },
                    label = { Text("Apellido") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp)
                )
            }

            OutlinedTextField(
                value = cifra,
                onValueChange = { cifra = it },
                label = { Text("Cifra / Código") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Column {
                Text(text = "Rol", fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(8.dp))
                ExposedDropdownMenuBox(
                    expanded = expandedRol,
                    onExpandedChange = { expandedRol = !expandedRol }
                ) {
                    OutlinedTextField(
                        value = rol.name,
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier.menuAnchor().fillMaxWidth(),
                        trailingIcon = { Icon(Icons.Default.ArrowDropDown, null) },
                        shape = RoundedCornerShape(12.dp)
                    )
                    ExposedDropdownMenu(
                        expanded = expandedRol,
                        onDismissRequest = { expandedRol = false }
                    ) {
                        Rol.values().forEach { selectionOption ->
                            DropdownMenuItem(
                                text = { Text(selectionOption.name) },
                                onClick = {
                                    rol = selectionOption
                                    expandedRol = false
                                }
                            )
                        }
                    }
                }
            }

            if (rol == Rol.ADMINISTRADOR) {
                OutlinedTextField(
                    value = nivelAcceso,
                    onValueChange = { nivelAcceso = it },
                    label = { Text("Nivel de Acceso (1-5)") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Usuario Activo")
                Switch(
                    checked = activo,
                    onCheckedChange = { activo = it }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { onRegistroSuccess() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Registrar Usuario", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
