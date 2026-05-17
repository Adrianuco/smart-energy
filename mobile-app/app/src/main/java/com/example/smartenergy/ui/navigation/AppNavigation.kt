package com.example.smartenergy.ui.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.smartenergy.model.Edificio
import com.example.smartenergy.ui.screen.DashboardScreen
import com.example.smartenergy.ui.screen.DetalleEdificioScreen
import com.example.smartenergy.ui.screen.EdificiosScreen
import com.example.smartenergy.ui.screen.GestionAlertasScreen
import com.example.smartenergy.ui.screen.HorariosScreen
import com.example.smartenergy.ui.screen.InfrastructureScreen
import com.example.smartenergy.ui.screen.LoginScreen
import com.example.smartenergy.ui.screen.ReportsScreen
import com.example.smartenergy.ui.screen.SettingsScreen
import com.example.smartenergy.ui.screen.listaEdificios

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        bottomBar = {
            val isLogin = currentDestination?.route?.contains("LoginRuta") == true
            if (currentDestination != null && !isLogin) {
                BottomBar(navController = navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = LoginRuta,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<LoginRuta> {
                LoginScreen(
                    onLoginClick = { _, _ -> navController.navigate(DashboardRuta) },
                    onGuestClick = { navController.navigate(DashboardRuta) }
                )
            }

            composable<DashboardRuta> {
                DashboardScreen(
                    onVerReportesClick = { navController.navigate(ReportesRuta) },
                    onVerEdificiosClick = { navController.navigate(EdificiosRuta) },
                    onVerAlertasClick = { navController.navigate(AlertasRuta) }
                )
            }

            composable<EdificiosRuta> {
                EdificiosScreen(
                    onEdificioClick = { nombre ->
                        navController.navigate(DetalleRuta(edificioNombre = nombre))
                    },
                    onAddEdificioClick = {
                        navController.navigate(InfraestructuraRuta())
                    }
                )
            }

            composable<DetalleRuta> { backStackEntry ->
                val destino = backStackEntry.toRoute<DetalleRuta>()
                val edificioSelected = listaEdificios.find { it.nombre == destino.edificioNombre }

                if (edificioSelected != null) {
                    DetalleEdificioScreen(
                        edificio = edificioSelected,
                        onAddAulasClick = { nombre ->
                            navController.navigate(InfraestructuraRuta(edificioNombre = nombre))
                        }
                    )
                }
            }

            composable<InfraestructuraRuta> { backStackEntry ->
                val destino = backStackEntry.toRoute<InfraestructuraRuta>()
                InfrastructureScreen(
                    initialBuildingName = destino.edificioNombre,
                    onBack = { navController.popBackStack() }
                )
            }

            composable<ReportesRuta> {
                ReportsScreen()
            }

            composable<AlertasRuta> {
                GestionAlertasScreen()
            }

            composable<AjustesRuta> {
                SettingsScreen()
            }

            composable<HorariosRuta> {
                HorariosScreen()
            }
        }
    }
}


