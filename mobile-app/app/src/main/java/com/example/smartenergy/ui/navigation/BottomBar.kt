package com.example.smartenergy.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState


@Composable
fun BottomBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val tabs = listOf(Tab.Home, Tab.Horarios, Tab.Settings)

    NavigationBar {
        tabs.forEach { tab ->
            val routeClassName = tab.route::class.qualifiedName ?: ""
            val isSelected = currentDestination?.route?.contains(routeClassName) == true || 
                             currentDestination?.route?.contains(tab.route::class.simpleName ?: "") == true

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(tab.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            inclusive = false
                        }
                        launchSingleTop = true
                    }
                },
                label = { Text(tab.label) },
                icon = { Icon(tab.icon, contentDescription = null) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF6750A4),
                    indicatorColor = Color(0xFFEADDFF)
                )
            )
        }
    }
}