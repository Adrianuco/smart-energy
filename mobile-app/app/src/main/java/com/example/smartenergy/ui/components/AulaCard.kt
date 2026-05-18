package com.example.smartenergy.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.example.smartenergy.ui.theme.AppColors

@Composable
fun AulaCard(roomName: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                text = "Aula $roomName",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(Modifier.height(8.dp))
            HorizontalDivider(
                modifier = Modifier.alpha(0.4f),
                color = MaterialTheme.colorScheme.outlineVariant
            )
            Spacer(Modifier.height(8.dp))

            // Clase Actual
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    color = AppColors.TagCurrentBg,
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        "ACTUAL",
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = AppColors.TagCurrentText
                    )
                }
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "Programación III (08:00 - 09:40)",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            Spacer(Modifier.height(8.dp))

            // Clase Próxima
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    color = AppColors.TagNextBg,
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        "PRÓXIMA",
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = AppColors.TagNextText
                    )
                }
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "Bases de Datos I (10:00 - 11:40)",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}