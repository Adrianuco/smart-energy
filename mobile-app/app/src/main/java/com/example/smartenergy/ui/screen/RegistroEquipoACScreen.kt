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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroEquipoACScreen(
    onRegistroSuccess: () -> Unit = {}
) {
    var marca by remember { mutableStateOf("") }
    var modelo by remember { mutableStateOf("") }
    var btu by remember { mutableStateOf("") }
    var eficiencia by remember { mutableStateOf("") }
    var potenciaMinima by remember { mutableStateOf("") }
    var potenciaNominal by remember { mutableStateOf("") }

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

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { onRegistroSuccess() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Registrar Equipo", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
