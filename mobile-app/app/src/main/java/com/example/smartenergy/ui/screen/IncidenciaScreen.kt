package com.example.smartenergy.ui.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.ReportProblem
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IncidenciaScreen(
    onEnviarClick: () -> Unit = {}
) {

    val tiposIncidencia = listOf(
        "Aire acondicionado dañado",
        "Fuga de agua",
        "Consumo excesivo",
        "Equipo no enciende",
        "Ruido extraño",
        "Temperatura inestable",
        "Otro"
    )

    val aulas = listOf(
        "A-101",
        "A-102",
        "B-201",
        "B-202",
        "C-301",
        "C-302"
    )

    var descripcion by remember { mutableStateOf("") }

    var expandedIncidencia by remember { mutableStateOf(false) }
    var incidenciaSeleccionada by remember {
        mutableStateOf("Seleccionar incidencia")
    }

    var expandedAula by remember { mutableStateOf(false) }
    var aulaSeleccionada by remember {
        mutableStateOf("Seleccionar aula")
    }

    // URI de la imagen seleccionada
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    // Launcher para abrir galería
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 120.dp, start = 20.dp, end = 20.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        Text(
            text = "Reportar Incidencia",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clickable {
                    launcher.launch("image/*")
                },
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(3.dp)
        ) {

            if (imageUri != null) {

                AsyncImage(
                    model = imageUri,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

            } else {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Icon(
                        imageVector = Icons.Default.Image,
                        contentDescription = null,
                        modifier = Modifier.size(60.dp),
                        tint = Color(0xFF6750A4)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Adjuntar imagen",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Toca para seleccionar una imagen",
                        color = Color.Gray
                    )
                }
            }
        }

        Column {

            Text(
                text = "Tipo de incidencia",
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            ExposedDropdownMenuBox(
                expanded = expandedIncidencia,
                onExpandedChange = {
                    expandedIncidencia = !expandedIncidencia
                }
            ) {

                OutlinedTextField(
                    value = incidenciaSeleccionada,
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    trailingIcon = {
                        Icon(
                            Icons.Default.ArrowDropDown,
                            contentDescription = null
                        )
                    },
                    shape = RoundedCornerShape(14.dp)
                )

                ExposedDropdownMenu(
                    expanded = expandedIncidencia,
                    onDismissRequest = {
                        expandedIncidencia = false
                    }
                ) {

                    tiposIncidencia.forEach { incidencia ->

                        DropdownMenuItem(
                            text = {
                                Text(incidencia)
                            },
                            onClick = {
                                incidenciaSeleccionada = incidencia
                                expandedIncidencia = false
                            },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.ReportProblem,
                                    contentDescription = null
                                )
                            }
                        )
                    }
                }
            }
        }

        Column {

            Text(
                text = "Seleccionar aula",
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            ExposedDropdownMenuBox(
                expanded = expandedAula,
                onExpandedChange = {
                    expandedAula = !expandedAula
                }
            ) {

                OutlinedTextField(
                    value = aulaSeleccionada,
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    trailingIcon = {
                        Icon(
                            Icons.Default.ArrowDropDown,
                            contentDescription = null
                        )
                    },
                    shape = RoundedCornerShape(14.dp)
                )

                ExposedDropdownMenu(
                    expanded = expandedAula,
                    onDismissRequest = {
                        expandedAula = false
                    }
                ) {

                    aulas.forEach { aula ->

                        DropdownMenuItem(
                            text = {
                                Text(aula)
                            },
                            onClick = {
                                aulaSeleccionada = aula
                                expandedAula = false
                            }
                        )
                    }
                }
            }
        }

        Column {

            Text(
                text = "Descripción",
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = descripcion,
                onValueChange = {
                    descripcion = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp),
                placeholder = {
                    Text("Describe la incidencia...")
                },
                shape = RoundedCornerShape(16.dp)
            )
        }

        Button(
            onClick = {
                onEnviarClick()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp)
        ) {

            Text(
                text = "Enviar Incidencia",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(30.dp))
    }
}