package com.example.smartenergy.ui.screen

import android.R.attr.content
import android.widget.ProgressBar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun DashboardScreen() {
    var consumo by remember { mutableStateOf(250) }
    var eficiencia by remember {mutableStateOf(0.8f)}
    var alertas by remember {mutableStateOf(5)}

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top=200.dp, bottom=100.dp ,start=30.dp, end=30.dp)
    ) {
        Card(
        modifier = Modifier
            .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
            ) {
                Text("Consumo")

                Spacer(modifier = Modifier.height(20.dp))

                Text("$consumo kWh", fontSize = 24.sp)

                Spacer(modifier = Modifier.height(40.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    LinearProgressIndicator(
                        progress = {eficiencia},
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text("${eficiencia * 100}%")
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text("Eficiencia")
            }

        }

        Spacer(modifier = Modifier.height(80.dp))
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceEvenly
                , horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier
                        .width(120.dp)
                        .height(60.dp),

                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Badge(
                            modifier = Modifier.size(30.dp)
                        ) {
                            Text("$alertas")
                        }
                        Text("Alertas")
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))

                Button(
                    modifier = Modifier
                        .width(170.dp),
                    onClick = {}
                ) {
                    Text("Reportar Incidencia")
                }

                Button(
                    modifier = Modifier
                        .width(170.dp),
                    onClick = {}
                ) {
                    Text("Ver Edificios")
                }

            }
            Spacer(modifier = Modifier.width(20.dp))
            Column(

            ) {
                Card(
                    modifier = Modifier.fillMaxSize()

                ) {
                    Column(
                        modifier = Modifier
                            .padding(10.dp)
                    ) {
                        Text("Resumen")
                    }
                }
            }
        }
    }
}