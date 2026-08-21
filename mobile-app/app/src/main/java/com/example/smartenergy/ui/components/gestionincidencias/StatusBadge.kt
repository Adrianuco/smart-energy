package com.example.smartenergy.ui.components.gestionincidencias

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.smartenergy.model.EstadoAlerta
import com.example.smartenergy.ui.theme.AppColors

@Composable
fun StatusBadge(estado: EstadoAlerta?) {
    val safeEstado = estado ?: EstadoAlerta.PENDIENTE
    val color = when (safeEstado) {
        EstadoAlerta.PENDIENTE -> AppColors.StatusError
        EstadoAlerta.ATENDIDA -> AppColors.StatusOk
    }
    val bgColor = when (safeEstado) {
        EstadoAlerta.PENDIENTE -> AppColors.StatusErrorBackground
        EstadoAlerta.ATENDIDA -> AppColors.StatusOkBackground
    }

    Surface(
        color = bgColor,
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = safeEstado.name.replace("_", " "),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelSmall,
            color = color
        )
    }
}
