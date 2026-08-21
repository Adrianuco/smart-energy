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
import com.example.smartenergy.model.Administrador
import com.example.smartenergy.model.ApoyoLogistico
import com.example.smartenergy.model.Rol
import com.example.smartenergy.viewmodel.usuarios.UsuariosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroUsuarioScreen(
    onRegistroSuccess: () -> Unit = {},
    viewModel: UsuariosViewModel
) {
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var cif by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rol by remember { mutableStateOf(Rol.APOYO_LOGISTICO) }
    var activo by remember { mutableStateOf(true) }
    var nivelAcceso by remember { mutableStateOf("") } // Solo para Administrador

    var expandedRol by remember { mutableStateOf(false) }
    var isSaving by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val mutationState by viewModel.mutationState.collectAsState()

    LaunchedEffect(mutationState) {
        when (mutationState) {
            is com.example.smartenergy.viewmodel.OperationState.Loading -> {
                isSaving = true
                errorMessage = null
            }
            is com.example.smartenergy.viewmodel.OperationState.Success -> {
                isSaving = false
                viewModel.resetMutationState()
                onRegistroSuccess()
            }
            is com.example.smartenergy.viewmodel.OperationState.Error -> {
                isSaving = false
                errorMessage = (mutationState as com.example.smartenergy.viewmodel.OperationState.Error).message
                viewModel.resetMutationState()
            }
            else -> {}
        }
    }

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
                value = cif,
                onValueChange = { cif = it },
                label = { Text("CIF") },
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

            if (errorMessage != null) {
                Text(
                    text = errorMessage!!,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (rol == Rol.ADMINISTRADOR) {
                        val admin = Administrador(
                            id = null,
                            nombre = nombre,
                            apellido = apellido,
                            cif = cif,
                            password = password,
                            activo = activo,
                            nivelAcceso = nivelAcceso
                        )
                        viewModel.agregarAdministrador(admin)
                    } else {
                        val logistico = ApoyoLogistico(
                            id = null,
                            nombre = nombre,
                            apellido = apellido,
                            cif = cif,
                            password = password,
                            activo = activo
                        )
                        viewModel.agregarLogistico(logistico)
                    }
                },
                enabled = !isSaving,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                if (isSaving) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text("Registrar Usuario", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
