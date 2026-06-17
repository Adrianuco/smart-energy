package com.example.smartenergy.ui.components.gestionincidencias

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.smartenergy.model.EstadoIncidencia
import com.example.smartenergy.ui.theme.AppColors

@Composable
fun StatusBadge(estado: EstadoIncidencia) {
    val color = when (estado) {
        EstadoIncidencia.PENDIENTE -> AppColors.StatusError
        EstadoIncidencia.EN_REVISION -> AppColors.StatusWarning
        EstadoIncidencia.RESUELTA -> AppColors.StatusOk
    }
    val bgColor = when (estado) {
        EstadoIncidencia.PENDIENTE -> AppColors.StatusErrorBackground
        EstadoIncidencia.EN_REVISION -> AppColors.StatusWarningBackground
        EstadoIncidencia.RESUELTA -> AppColors.StatusOkBackground
    }

    Surface(
        color = bgColor,
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = estado.name.replace("_", " "),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelSmall,
            color = color
        )
    }
}
