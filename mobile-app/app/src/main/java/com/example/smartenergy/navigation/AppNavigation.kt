package com.example.smartenergy.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.smartenergy.ui.screen.*

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry.value?.destination

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
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
                    onVerAlertasClick = { navController.navigate(AlertasRuta) },
                    onGestionIncidenciasClick = { navController.navigate(GestionIncidenciasRuta) },
                    onReportarClick = { navController.navigate(IncidenciaRuta) },
                    onVerEquiposClick = { navController.navigate(EquiposRegistradosRuta) }
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

            composable<IncidenciaRuta> {
                IncidenciaScreen(onEnviarClick = { navController.popBackStack() })
            }

            composable<DetalleRuta> { backStackEntry ->
                val destino = backStackEntry.toRoute<DetalleRuta>()
                val edificioSelected = listaEdificios.find { it.nombre == destino.edificioNombre }

                if (edificioSelected != null) {
                    DetalleEdificioScreen(
                        edificio = edificioSelected,
                        onAddAulasClick = { nombre ->
                            navController.navigate(InfraestructuraRuta(edificioNombre = nombre))
                        },
                        onGestionIncidenciasClick = {
                            navController.navigate(GestionIncidenciasRuta)
                        }
                    )
                }
            }

            composable<InfraestructuraRuta> { backStackEntry ->
                val destino = backStackEntry.toRoute<InfraestructuraRuta>()
                InfrastructureScreen(
                    initialBuildingName = destino.edificioNombre,
                    onAddACClick = { navController.navigate(RegistroEquipoACRuta) },
                    onBack = { navController.popBackStack() }
                )
            }

            composable<ReportesRuta> {
                ReportsScreen()
            }

            composable<AlertasRuta> {
                GestionAlertasScreen(
                    onAtenderAlerta = { id -> navController.navigate(AtenderAlertaRuta(id)) }
                )
            }

            composable<AjustesRuta> {
                SettingsScreen(
                    onAddUserClick = { navController.navigate(RegistroUsuarioRuta) }
                )
            }

            composable<HorariosRuta> {
                HorariosScreen()
            }

            composable<RegistroEquipoACRuta> {
                RegistroEquipoACScreen(onRegistroSuccess = { navController.popBackStack() })
            }

            composable<RegistroUsuarioRuta> {
                RegistroUsuarioScreen(onRegistroSuccess = { navController.popBackStack() })
            }

            composable<GestionIncidenciasRuta> {
                GestionIncidenciasScreen(
                    onAtenderIncidencia = { id -> navController.navigate(AtenderIncidenciaRuta(id)) }
                )
            }

            composable<EquiposRegistradosRuta> {
                EquiposRegistradosScreen(
                    onAddACClick = { navController.navigate(RegistroEquipoACRuta) },
                    onBack = { navController.popBackStack() }
                )
            }

            composable<AtenderAlertaRuta> { backStackEntry ->
                val destino = backStackEntry.toRoute<AtenderAlertaRuta>()
                AtenderAlertaScreen(
                    alertaId = destino.alertaId,
                    onBack = { navController.popBackStack() }
                )
            }

            composable<AtenderIncidenciaRuta> { backStackEntry ->
                val destino = backStackEntry.toRoute<AtenderIncidenciaRuta>()
                AtenderIncidenciaScreen(
                    incidenciaId = destino.incidenciaId,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}
