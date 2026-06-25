package com.example.smartenergy.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.smartenergy.service.ServiceLocator
import com.example.smartenergy.ui.screen.*
import com.example.smartenergy.viewmodel.alertas.*
import com.example.smartenergy.viewmodel.dashboard.*
import com.example.smartenergy.viewmodel.edificio.*
import com.example.smartenergy.viewmodel.infrastructure.*
import com.example.smartenergy.viewmodel.login.*
import com.example.smartenergy.viewmodel.reports.*
import com.example.smartenergy.viewmodel.settings.*
import com.example.smartenergy.viewmodel.horarios.*
import com.example.smartenergy.viewmodel.equipo.*
import com.example.smartenergy.viewmodel.usuarios.*
import com.example.smartenergy.viewmodel.incidencias.*

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
                val vm: LoginViewModel = viewModel(
                    factory = LoginViewModelFactory(ServiceLocator.authRepository)
                )
                LoginScreen(
                    onLoginClick = { _, _ -> navController.navigate(DashboardRuta) },
                    onGuestClick = { navController.navigate(DashboardRuta) },
                    viewModel = vm
                )
            }

            composable<DashboardRuta> {
                val vm: DashboardViewModel = viewModel(
                    factory = DashboardViewModelFactory(ServiceLocator.dashboardRepository)
                )
                DashboardScreen(
                    onVerReportesClick = { navController.navigate(ReportesRuta) },
                    onVerEdificiosClick = { navController.navigate(EdificiosRuta) },
                    onVerAlertasClick = { navController.navigate(AlertasRuta) },
                    onGestionIncidenciasClick = { navController.navigate(GestionIncidenciasRuta) },
                    onReportarClick = { navController.navigate(IncidenciaRuta) },
                    onVerEquiposClick = { navController.navigate(EquiposRegistradosRuta) },
                    viewModel = vm
                )
            }

            composable<EdificiosRuta> {
                val vm: EdificiosViewModel = viewModel(
                    factory = EdificiosViewModelFactory(ServiceLocator.edificioRepository)
                )
                EdificiosScreen(
                    onEdificioClick = { id, nombre ->
                        navController.navigate(DetalleRuta(edificioId = id, edificioNombre = nombre))
                    },
                    onAddEdificioClick = {
                        navController.navigate(InfraestructuraRuta())
                    },
                    viewModel = vm
                )
            }

            composable<IncidenciaRuta> {
                val vm: CrearIncidenciaViewModel = viewModel(
                    factory = CrearIncidenciaViewModelFactory(
                        ServiceLocator.incidenciaRepository,
                        ServiceLocator.aulaRepository
                    )
                )
                IncidenciaScreen(
                    onEnviarClick = { navController.popBackStack() },
                    viewModel = vm
                )
            }

            composable<DetalleRuta> { backStackEntry ->
                val destino = backStackEntry.toRoute<DetalleRuta>()
                val vm: DetalleEdificioViewModel = viewModel(
                    factory = DetalleEdificioViewModelFactory(
                        ServiceLocator.edificioRepository,
                        ServiceLocator.registroOperativoRepository
                    )
                )
                DetalleEdificioScreen(
                    edificioId = destino.edificioId,
                    edificioNombre = destino.edificioNombre,
                    viewModel = vm,
                    onAddAulasClick = { nombre ->
                        navController.navigate(InfraestructuraRuta(edificioNombre = nombre))
                    },
                    onGestionIncidenciasClick = {
                        navController.navigate(GestionIncidenciasRuta)
                    }
                )
            }

            composable<InfraestructuraRuta> { backStackEntry ->
                val destino = backStackEntry.toRoute<InfraestructuraRuta>()
                val vm: InfrastructureViewModel = viewModel(
                    factory = InfrastructureViewModelFactory(
                        ServiceLocator.equipoRepository,
                        ServiceLocator.edificioRepository,
                        ServiceLocator.aulaRepository
                    )
                )
                InfrastructureScreen(
                    initialBuildingName = destino.edificioNombre,
                    onAddACClick = { navController.navigate(RegistroEquipoACRuta) },
                    onBack = { navController.popBackStack() },
                    viewModel = vm
                )
            }

            composable<ReportesRuta> {
                val vm: ReportsViewModel = viewModel(
                    factory = ReportsViewModelFactory(ServiceLocator.edificioRepository)
                )
                ReportsScreen(viewModel = vm)
            }

            composable<AlertasRuta> {
                val viewmodel: AlertasViewModel = viewModel(
                    factory = AlertasViewModelFactory(ServiceLocator.alertaRepository)
                )
                GestionAlertasScreen(
                    onAtenderAlerta = { id -> navController.navigate(AtenderAlertaRuta(id)) },
                    viewModel = viewmodel
                )
            }

            composable<AjustesRuta> {
                val settingsVm: SettingsViewModel = viewModel(
                    factory = SettingsViewModelFactory(ServiceLocator.configSistemaRepository)
                )
                val usuariosVm: UsuariosViewModel = viewModel(
                    factory = UsuariosViewModelFactory(
                        ServiceLocator.administradorRepository,
                        ServiceLocator.apoyoLogisticaRepository
                    )
                )
                SettingsScreen(
                    onAddUserClick = { navController.navigate(RegistroUsuarioRuta) },
                    settingsViewModel = settingsVm,
                    usuariosViewModel = usuariosVm
                )
            }

            composable<HorariosRuta> {
                val vm: HorariosViewModel = viewModel(
                    factory = HorariosViewModelFactory(ServiceLocator.horarioAcademicoRepository)
                )
                HorariosScreen(viewModel = vm)
            }

            composable<RegistroEquipoACRuta> {
                val vm: RegistrarEquipoViewModel = viewModel(
                    factory = RegistrarEquipoViewModelFactory(ServiceLocator.equipoRepository)
                )
                RegistroEquipoACScreen(
                    onRegistroSuccess = { navController.popBackStack() },
                    viewModel = vm
                )
            }

            composable<RegistroUsuarioRuta> {
                val vm: UsuariosViewModel = viewModel(
                    factory = UsuariosViewModelFactory(
                        ServiceLocator.administradorRepository,
                        ServiceLocator.apoyoLogisticaRepository
                    )
                )
                RegistroUsuarioScreen(
                    onRegistroSuccess = { navController.popBackStack() },
                    viewModel = vm
                )
            }

            composable<GestionIncidenciasRuta> {
                val vm: IncidenciasViewModel = viewModel(
                    factory = IncidenciasViewModelFactory(ServiceLocator.incidenciaRepository)
                )
                GestionIncidenciasScreen(
                    onAtenderIncidencia = { id -> navController.navigate(AtenderIncidenciaRuta(id)) },
                    viewModel = vm
                )
            }

            composable<EquiposRegistradosRuta> {
                val vm: EquiposViewModel = viewModel(
                    factory = EquiposViewModelFactory(ServiceLocator.equipoRepository)
                )
                EquiposRegistradosScreen(
                    onAddACClick = { navController.navigate(RegistroEquipoACRuta) },
                    onBack = { navController.popBackStack() },
                    viewModel = vm
                )
            }

            composable<AtenderAlertaRuta> { backStackEntry ->
                val destino = backStackEntry.toRoute<AtenderAlertaRuta>()
                val vm: AtenderAlertaViewModel = viewModel(
                    factory = AtenderAlertaViewModelFactory(
                        ServiceLocator.alertaRepository,
                        ServiceLocator.registroOperativoRepository
                    )
                )
                AtenderAlertaScreen(
                    alertaId = destino.alertaId,
                    onBack = { navController.popBackStack() },
                    viewModel = vm
                )
            }

            composable<AtenderIncidenciaRuta> { backStackEntry ->
                val destino = backStackEntry.toRoute<AtenderIncidenciaRuta>()
                val vm: AtenderIncidenciaViewModel = viewModel(
                    factory = AtenderIncidenciaViewModelFactory(ServiceLocator.incidenciaRepository)
                )
                AtenderIncidenciaScreen(
                    incidenciaId = destino.incidenciaId,
                    onBack = { navController.popBackStack() },
                    viewModel = vm
                )
            }
        }
    }
}
