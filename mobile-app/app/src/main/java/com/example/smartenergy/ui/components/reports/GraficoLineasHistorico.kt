package com.example.smartenergy.ui.components.reports

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.smartenergy.ui.components.shared.SmartEnergyLineChart
import com.example.smartenergy.ui.screen.ConsumoHistorico

@Composable
fun GraficoLineasHistorico(historico: List<ConsumoHistorico>) {
    SmartEnergyLineChart(
        data = historico.map { it.consumo },
        bottomLabels = historico.map { it.label },
        chartHeight = 200.dp,
        showAxis = true
    )
}
