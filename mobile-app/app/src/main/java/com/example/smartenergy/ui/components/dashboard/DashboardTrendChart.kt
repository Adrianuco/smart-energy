package com.example.smartenergy.ui.components.dashboard

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.smartenergy.ui.components.shared.SmartEnergyLineChart

@Composable
fun DashboardTrendChart(
    data: List<Float>,
    labels: List<String>
) {
    SmartEnergyLineChart(
        data = data,
        bottomLabels = labels,
        chartHeight = 160.dp,
        showAxis = true
    )
}
