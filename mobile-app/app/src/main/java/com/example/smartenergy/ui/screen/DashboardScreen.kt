package com.example.smartenergy.ui.screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.TrendingUp
import androidx.compose.material.icons.outlined.AcUnit
import androidx.compose.material.icons.outlined.Bolt
import androidx.compose.material.icons.outlined.Business
import androidx.compose.material.icons.outlined.Campaign
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Savings
import androidx.compose.material.icons.outlined.Speed
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartenergy.ui.components.dashboard.DashboardTrendChart
import com.example.smartenergy.ui.components.dashboard.MetricCard
import com.example.smartenergy.ui.components.dashboard.QuickActionCard
import com.example.smartenergy.ui.theme.AppColors
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onVerEdificiosClick: () -> Unit,
    onVerReportesClick: () -> Unit,
    onVerAlertasClick: () -> Unit,
    onGestionIncidenciasClick: () -> Unit,
    onReportarClick: () -> Unit,
    onVerEquiposClick: () -> Unit
) {
    var consumo by remember { mutableIntStateOf(250) }
    var ahorroEnergia by remember { mutableFloatStateOf(0.35f) } // Representa 35% de ahorro
    var alertas by remember { mutableIntStateOf(5) }
    var incidencias by remember { mutableIntStateOf(2) }

    // Animate the savings ring on first load
    var animationTriggered by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { animationTriggered = true }
    val animatedAhorro by animateFloatAsState(
        targetValue = if (animationTriggered) ahorroEnergia else 0f,
        animationSpec = tween(1200),
        label = "savingsAnim"
    )

    val today = LocalDate.now()
    val greeting = when (today.atStartOfDay().hour) {
        in 0..11 -> "Buenos días"
        in 12..17 -> "Buenas tardes"
        else -> "Buenas noches"
    }
    val dateFormatted = today.format(
        DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM", Locale("es", "MX"))
    ).replaceFirstChar { it.uppercase() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Avatar
                        Surface(
                            modifier = Modifier.size(38.dp),
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.primaryContainer
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    "A",
                                    style = MaterialTheme.typography.titleSmall,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                greeting,
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Text(
                                dateFormatted,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                },
                actions = {
                    IconButton(onClick = onVerAlertasClick) {
                        BadgedBox(
                            badge = {
                                Badge(containerColor = AppColors.StatusError) {
                                    Text(
                                        "$alertas",
                                        color = MaterialTheme.colorScheme.onError
                                    )
                                }
                            }
                        ) {
                            Icon(
                                Icons.Outlined.Notifications,
                                contentDescription = "Alertas",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
                .padding(bottom = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // ════════════════════════════════════════
            // 1. HERO CARD — Main Focal Point (Energy Savings)
            // ════════════════════════════════════════
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {
                    // Top row: label + live badge
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Consumo Energético",
                            style = MaterialTheme.typography.labelLarge,
                            color = Color.White.copy(alpha = 0.75f)
                        )
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = Color.White.copy(alpha = 0.15f)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .clip(CircleShape)
                                        .background(AppColors.StatusOk)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    "En vivo",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.White
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Center: Consumption value + Savings ring
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Left: big value
                        Column {
                            Text(
                                "$consumo",
                                style = MaterialTheme.typography.displayLarge.copy(
                                    fontSize = 48.sp,
                                    fontWeight = FontWeight.Bold
                                ),
                                color = Color.White
                            )
                            Text(
                                "kWh consumidos hoy",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.White.copy(alpha = 0.7f)
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            // Delta indicator
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = AppColors.StatusOk.copy(alpha = 0.2f)
                            ) {
                                Row(
                                    modifier = Modifier.padding(
                                        horizontal = 10.dp,
                                        vertical = 4.dp
                                    ),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        Icons.AutoMirrored.Outlined.TrendingUp,
                                        contentDescription = null,
                                        modifier = Modifier.size(14.dp),
                                        tint = AppColors.StatusOk
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        "-12% vs ayer",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = AppColors.StatusOk
                                    )
                                }
                            }
                        }

                        // Right: Savings ring
                        Box(
                            modifier = Modifier.size(110.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            val ringTrack = Color.White.copy(alpha = 0.15f)
                            val ringColor = AppColors.StatusOk

                            Canvas(modifier = Modifier.fillMaxSize()) {
                                val stroke = 10.dp.toPx()
                                val pad = stroke / 2
                                drawArc(
                                    color = ringTrack,
                                    startAngle = -225f,
                                    sweepAngle = 270f,
                                    useCenter = false,
                                    style = Stroke(width = stroke, cap = StrokeCap.Round),
                                    topLeft = Offset(pad, pad),
                                    size = androidx.compose.ui.geometry.Size(
                                        size.width - stroke,
                                        size.height - stroke
                                    )
                                )
                                drawArc(
                                    color = ringColor,
                                    startAngle = -225f,
                                    sweepAngle = 270f * animatedAhorro,
                                    useCenter = false,
                                    style = Stroke(width = stroke, cap = StrokeCap.Round),
                                    topLeft = Offset(pad, pad),
                                    size = androidx.compose.ui.geometry.Size(
                                        size.width - stroke,
                                        size.height - stroke
                                    )
                                )
                            }

                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    "${(animatedAhorro * 100).toInt()}%",
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        fontWeight = FontWeight.Bold
                                    ),
                                    color = Color.White
                                )
                                Text(
                                    "Ahorro",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.White.copy(alpha = 0.7f)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // SECCION DE LAS METRIC CARDS
            Text(
                "Estado del Sistema",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                MetricCard(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Outlined.Warning,
                    label = "Alertas Activas",
                    value = "$alertas",
                    iconTint = AppColors.StatusError,
                    iconBg = AppColors.StatusErrorBackground,
                    onClick = onVerAlertasClick
                )
                MetricCard(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Outlined.Business,
                    label = "Edificios",
                    value = "5",
                    iconTint = MaterialTheme.colorScheme.primary,
                    iconBg = MaterialTheme.colorScheme.primaryContainer,
                    onClick = onVerEdificiosClick
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                MetricCard(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Outlined.AcUnit,
                    label = "Equipos Activos",
                    value = "18",
                    iconTint = MaterialTheme.colorScheme.secondary,
                    iconBg = MaterialTheme.colorScheme.secondaryContainer,
                    onClick = onVerEquiposClick
                )
                MetricCard(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Outlined.ErrorOutline,
                    label = "Incidencias",
                    value = "$incidencias",
                    iconTint = AppColors.StatusError,
                    iconBg = AppColors.StatusErrorBackground,
                    onClick = onGestionIncidenciasClick
                )
            }


            Spacer(modifier = Modifier.height(24.dp))


            // SECCION DEL GRAFICO
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onVerReportesClick),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(2.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                "Tendencia de Consumo",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Text(
                                "Últimas 7 horas · kWh",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.primaryContainer
                        ) {
                            Text(
                                "Ver reportes",
                                modifier = Modifier.padding(
                                    horizontal = 12.dp,
                                    vertical = 6.dp
                                ),
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    DashboardTrendChart(
                        data = listOf(32f, 45f, 38f, 67f, 52f, 78f, 60f),
                        labels = listOf("8am", "9am", "10am", "11am", "12pm", "1pm", "2pm")
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // SECCION DE ACCIONES RAPIDAS
            Text(
                "Acciones Rápidas",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                QuickActionCard(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Outlined.Campaign,
                    label = "Reportar\nIncidencia",
                    color = MaterialTheme.colorScheme.primary,
                    bg = MaterialTheme.colorScheme.primaryContainer,
                    onClick = onReportarClick
                )
                QuickActionCard(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Outlined.Business,
                    label = "Ver\nEdificios",
                    color = MaterialTheme.colorScheme.secondary,
                    bg = MaterialTheme.colorScheme.secondaryContainer,
                    onClick = onVerEdificiosClick
                )
                QuickActionCard(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Outlined.ErrorOutline,
                    label = "Gestionar\nIncidencias",
                    color = AppColors.StatusWarning,
                    bg = AppColors.StatusWarningBackground,
                    onClick = onGestionIncidenciasClick
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
