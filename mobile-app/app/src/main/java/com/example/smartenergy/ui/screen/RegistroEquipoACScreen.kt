package com.example.smartenergy.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.runtime.collectAsState
import com.example.smartenergy.model.Equipo
import com.example.smartenergy.viewmodel.equipo.RegistrarEquipoState
import com.example.smartenergy.viewmodel.equipo.RegistrarEquipoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroEquipoACScreen(
    onRegistroSuccess: () -> Unit = {},
    viewModel: RegistrarEquipoViewModel
) {
    var marca by remember { mutableStateOf("") }
    var modelo by remember { mutableStateOf("") }
    var btu by remember { mutableStateOf("") }
    var eficiencia by remember { mutableStateOf("") }
    var potenciaMinima by remember { mutableStateOf("") }
    var potenciaNominal by remember { mutableStateOf("") }

    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registrar Equipo AC") }
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
                text = "Información del Equipo",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            OutlinedTextField(
                value = marca,
                onValueChange = { marca = it },
                label = { Text("Marca") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            OutlinedTextField(
                value = modelo,
                onValueChange = { modelo = it },
                label = { Text("Modelo") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = btu,
                    onValueChange = { btu = it },
                    label = { Text("BTU") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = eficiencia,
                    onValueChange = { eficiencia = it },
                    label = { Text("Eficiencia") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp)
                )
            }

            OutlinedTextField(
                value = potenciaMinima,
                onValueChange = { potenciaMinima = it },
                label = { Text("Potencia Mínima (kW)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                shape = RoundedCornerShape(12.dp)
            )

            OutlinedTextField(
                value = potenciaNominal,
                onValueChange = { potenciaNominal = it },
                label = { Text("Potencia Nominal (kW)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                shape = RoundedCornerShape(12.dp)
            )

            if (state is RegistrarEquipoState.Error) {
                Text(
                    text = (state as RegistrarEquipoState.Error).message,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    val equipo = Equipo(
                        marca = marca,
                        modelo = modelo,
                        btu = btu.toIntOrNull() ?: 12000,
                        eficiencia = eficiencia,
                        operativo = true,
                        potenciaMinima = potenciaMinima.toDoubleOrNull() ?: 0.5,
                        potenciaNominal = potenciaNominal.toDoubleOrNull() ?: 2.5
                    )
                    viewModel.registrar(equipo) { success ->
                        if (success) {
                            onRegistroSuccess()
                        }
                    }
                },
                enabled = state !is RegistrarEquipoState.Loading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                if (state is RegistrarEquipoState.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text("Registrar Equipo", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
