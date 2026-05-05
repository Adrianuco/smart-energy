package com.example.smartenergy.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState


@Composable
fun BottomBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(

    ) {
        NavigationBarItem(
            selected = currentDestination?.route?.contains("DashboardRuta") == true,
            onClick = {
                navController.navigate(DashboardRuta) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        inclusive = false
                    }
                    launchSingleTop = true
                }
            },
            label = { Text("Inicio") },
            icon = { Icon(Icons.Default.Home, contentDescription = null) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF6750A4),
                indicatorColor = Color(0xFFEADDFF)
            )
        )

    }
}