package com.example.smartenergy.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "http://10.0.2.2:8181/api/"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
    }

    val alertaApiService: AlertaApiService by lazy {
        retrofit.create(AlertaApiService::class.java)
    }

    val aulaApiService: AulaApiService by lazy {
        retrofit.create(AulaApiService::class.java)
    }

    val authApiService: AuthApiService by lazy {
        retrofit.create(AuthApiService::class.java)
    }

    val dashboardApiService: DashboardApiService by lazy {
        retrofit.create(DashboardApiService::class.java)
    }

    val edificioApiService: EdificioApiService by lazy {
        retrofit.create(EdificioApiService::class.java)
    }

    val equipoApiService: EquipoApiService by lazy {
        retrofit.create(EquipoApiService::class.java)
    }

    val incidenciaApiService: IncidenciaApiService by lazy {
        retrofit.create(IncidenciaApiService::class.java)
    }

    val administradorApiService: AdministradorApiService by lazy {
        retrofit.create(AdministradorApiService::class.java)
    }

    val configSistemaApiService: ConfigSistemaApiService by lazy {
        retrofit.create(ConfigSistemaApiService::class.java)
    }

    val apoyoLogisticaApiService: ApoyoLogisticaApiService by lazy {
        retrofit.create(ApoyoLogisticaApiService::class.java)
    }

    val horarioAcademicoApiService: HorarioAcademicoApiService by lazy {
        retrofit.create(HorarioAcademicoApiService::class.java)
    }

    val asignacionEdificoApiService: AsignacionEdificoApiService by lazy {
        retrofit.create(AsignacionEdificoApiService::class.java)
    }

    val registroOperativoApiService: RegistroOperativoApiService by lazy {
        retrofit.create(RegistroOperativoApiService::class.java)
    }
}
