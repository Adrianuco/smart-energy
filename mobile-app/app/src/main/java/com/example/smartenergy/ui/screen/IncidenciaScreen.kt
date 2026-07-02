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

import androidx.compose.runtime.collectAsState
import com.example.smartenergy.model.Aula
import com.example.smartenergy.model.EstadoAlerta
import com.example.smartenergy.model.Incidencia
import com.example.smartenergy.viewmodel.incidencias.CrearIncidenciaState
import com.example.smartenergy.viewmodel.incidencias.CrearIncidenciaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IncidenciaScreen(
    onEnviarClick: () -> Unit = {},
    viewModel: CrearIncidenciaViewModel
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state) {
        if (state is CrearIncidenciaState.Success) {
            onEnviarClick()
            viewModel.resetState()
        }
    }

    val tiposIncidencia = listOf(
        "Desperdicio Energético",
        "Falta de Climatización",
        "Problemas Eléctricos",
        "Otro"
    )

    val dbAulasState by viewModel.aulas.collectAsState()

    val aulas = remember(dbAulasState) {
        val stateVal = dbAulasState
        if (stateVal is CrearIncidenciaState.AulasSucces) {
            stateVal.aulas.map { it.codigo.removePrefix("Aula ").trim() }.distinct().sorted()
        } else {
            emptyList()
        }
    }

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

        if (state is CrearIncidenciaState.Error) {
            Text(
                text = (state as CrearIncidenciaState.Error).message,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Button(
            onClick = {
                val selectedAula = if (aulaSeleccionada != "Seleccionar aula") {
                    val stateVal = dbAulasState
                    val listAulas = if (stateVal is CrearIncidenciaState.AulasSucces) stateVal.aulas else emptyList()
                    listAulas.find { it.codigo.removePrefix("Aula ").trim() == aulaSeleccionada || it.codigo == aulaSeleccionada }
                        ?: Aula(
                            id = null,
                            codigo = "Aula $aulaSeleccionada",
                            piso = 1,
                            eficiencia = 100f,
                            equipo = null
                        )
                } else null
                val incidencia = Incidencia(
                    descripcion = descripcion,
                    tipoIncidencia = if (incidenciaSeleccionada != "Seleccionar incidencia") incidenciaSeleccionada else "Otro",
                    aula = selectedAula,
                    estado = EstadoAlerta.PENDIENTE
                )
                viewModel.save(incidencia)
            },
            enabled = state !is CrearIncidenciaState.Loading,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            if (state is CrearIncidenciaState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text(
                    text = "Enviar Incidencia",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))
    }
}