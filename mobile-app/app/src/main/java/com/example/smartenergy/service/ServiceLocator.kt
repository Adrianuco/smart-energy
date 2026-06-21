package com.example.smartenergy.service
import com.example.smartenergy.repository.*

object ServiceLocator {

    private val alertaApi =
        RetrofitClient.alertaApiService

    private val aulaApi =
        RetrofitClient.aulaApiService

    private val authApi =
        RetrofitClient.authApiService

    private val dashboardApi =
        RetrofitClient.dashboardApiService

    private val edificioApi =
        RetrofitClient.edificioApiService

    private val equipoApi =
        RetrofitClient.equipoApiService

    private val incidenciaApi =
        RetrofitClient.incidenciaApiService

    private val administradorApi =
        RetrofitClient.administradorApiService

    private val configSistemaApi =
        RetrofitClient.configSistemaApiService

    private val apoyoLogisticaApi =
        RetrofitClient.apoyoLogisticaApiService

    private val horarioAcademicoApi =
        RetrofitClient.horarioAcademicoApiService

    private val asignacionEdificioApi =
        RetrofitClient.asignacionEdificoApiService

    private val registroOperativoApi =
        RetrofitClient.registroOperativoApiService



    val alertaRepository =
        AlertaRepository(alertaApi)


    val aulaRepository =
        AulaRepository(aulaApi)


    val authRepository =
        AuthRepository(authApi)


    val dashboardRepository =
        DashboardRepository(dashboardApi)


    val edificioRepository =
        EdificioRepository(edificioApi)


    val equipoRepository =
        EquipoRepository(equipoApi)


    val incidenciaRepository =
        IncidenciaRepository(incidenciaApi)


    val administradorRepository =
        AdministradorRepository(administradorApi)


    val configSistemaRepository =
        ConfigSistemaRepository(configSistemaApi)


    val apoyoLogisticaRepository =
        ApoyoLogisticoRepository(apoyoLogisticaApi)


    val horarioAcademicoRepository =
        HorarioAcademicoRepository(horarioAcademicoApi)


    val asignacionEdificioRepository =
        AsignacionEdificioRepository(asignacionEdificioApi)


    val registroOperativoRepository =
        RegistroOperativoRepository(registroOperativoApi)

}