package com.example.smartenergy.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.chart.line.lineSpec
import com.patrykandpatrick.vico.compose.chart.scroll.rememberChartScrollSpec
import com.patrykandpatrick.vico.compose.component.shapeComponent
import com.patrykandpatrick.vico.compose.component.textComponent
import com.patrykandpatrick.vico.compose.dimensions.dimensionsOf
import com.patrykandpatrick.vico.core.axis.AxisItemPlacer
import com.patrykandpatrick.vico.core.chart.DefaultPointConnector
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.entry.entryModelOf
import com.example.smartenergy.ui.theme.AppColors

@Composable
fun SmartEnergyLineChart(
    data: List<Float>,
    modifier: Modifier = Modifier,
    bottomLabels: List<String>? = null,
    chartHeight: Dp = 180.dp,
    showAxis: Boolean = true,
    lineColorArgb: Int? = null
) {
    if (data.isEmpty()) return

    val primary = MaterialTheme.colorScheme.primary
    val secondary = MaterialTheme.colorScheme.onSurfaceVariant
    val resolvedLineColor = lineColorArgb ?: primary.toArgb()

    val model = remember(data) { entryModelOf(*data.toTypedArray()) }

    val chart = lineChart(
        lines = listOf(
            lineSpec(
                lineColor = androidx.compose.ui.graphics.Color(resolvedLineColor),
                lineThickness = 2.5.dp,
                pointConnector = DefaultPointConnector(cubicStrength = 0.35f),
                point = shapeComponent(
                    shape = Shapes.pillShape,
                    color = androidx.compose.ui.graphics.Color(resolvedLineColor),
                    dynamicShader = null
                ),
                pointSize = 7.dp
            )
        )
    )

    val labelTextColor = secondary.toArgb()

    val bottomAxis = if (showAxis && bottomLabels != null) {
        rememberBottomAxis(
            label = textComponent(
                color = androidx.compose.ui.graphics.Color(labelTextColor),
                textSize = 11.sp,
                padding = dimensionsOf(top = 4.dp)
            ),
            tick = null,
            guideline = null,
            valueFormatter = { value, _ ->
                val idx = value.toInt()
                bottomLabels.getOrElse(idx) { "" }
            },
            itemPlacer = AxisItemPlacer.Horizontal.default(spacing = 1)
        )
    } else null

    val startAxis = if (showAxis) {
        rememberStartAxis(
            label = textComponent(
                color = androidx.compose.ui.graphics.Color(labelTextColor),
                textSize = 10.sp,
                padding = dimensionsOf(end = 4.dp)
            ),
            tick = null,
            guideline = com.patrykandpatrick.vico.compose.component.lineComponent(
                color = androidx.compose.ui.graphics.Color(AppColors.ChartGrid.toArgb()),
                thickness = 1.dp
            ),
            itemPlacer = AxisItemPlacer.Vertical.default(maxItemCount = 4)
        )
    } else null

    Chart(
        modifier = modifier
            .fillMaxWidth()
            .height(chartHeight),
        chart = chart,
        model = model,
        startAxis = startAxis,
        bottomAxis = bottomAxis,
        chartScrollSpec = rememberChartScrollSpec(isScrollEnabled = false)
    )
}
