package com.smartenergy.backendapi.service;


import com.smartenergy.backendapi.model.*;
import com.smartenergy.backendapi.repository.AulaRepository;
import com.smartenergy.backendapi.repository.EquipoRepository;
import com.smartenergy.backendapi.repository.HorarioAcademicoRepository;
import com.smartenergy.backendapi.repository.IConfigSistemaRepository;
import com.smartenergy.backendapi.repository.RegistroOperativoRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class MonitoreoService {
    private final HorarioAcademicoRepository horarioAcademicoRepository;
    private final RegistroOperativoRepository registroOperativoRepository;
    private final IConfigSistemaRepository configSistemaRepository;
    private final AulaRepository aulaRepository;
    private final AlertaService alertaService;
    private final RegistroOperativoService registroOperativoService;
    private final EquipoRepository equipoRepository;

    public MonitoreoService(
            HorarioAcademicoRepository horarioAcademicoRepository,
            RegistroOperativoRepository registroOperativoRepository,
            IConfigSistemaRepository configSistemaRepository,
            AulaRepository aulaRepository,
            AlertaService alertaService,
            RegistroOperativoService registroOperativoService,
            EquipoRepository equipoRepository
    ) {
        this.horarioAcademicoRepository = horarioAcademicoRepository;
        this.registroOperativoRepository = registroOperativoRepository;
        this.configSistemaRepository = configSistemaRepository;
        this.aulaRepository = aulaRepository;
        this.alertaService = alertaService;
        this.registroOperativoService = registroOperativoService;
        this.equipoRepository = equipoRepository;
    }

    @Scheduled(fixedRate = 60000)
    public void monitorear() {
        List<Aula> aulas = aulaRepository.findAll();

        for (Aula aula : aulas) {
            verificarAula(aula);
        }
    }

    public void verificarAula(Aula aula) {

        int diaActual = LocalDateTime.now().getDayOfWeek().getValue();
        LocalTime horaActual = LocalTime.now();
        Optional<HorarioAcademico> proximaClase = horarioAcademicoRepository.findProximaClase(aula, diaActual, horaActual).stream().findFirst();
        boolean hayClase = horarioAcademicoRepository.hayClase(aula, diaActual, horaActual);

        // Intentar obtener el equipo desde la relación lazy o forzar consulta directa si retorna null
        Equipo equipo = aula.getEquipo();
        if (equipo == null) {
            equipo = equipoRepository.findByAula(aula).orElse(null);
        }

        RegistroOperativo estado = null;
        if (equipo != null) {
            estado = registroOperativoRepository.findByEquipoAndFinIsNull(equipo).orElse(null);
            
            if (estado == null) {
                // Autocuración: inicializar el registro operativo si no tiene ninguno activo en DB
                registroOperativoService.cambiarEstado(equipo, equipo.getEstado());
                estado = registroOperativoRepository.findByEquipoAndFinIsNull(equipo).orElse(null);
            }
        }

        ConfigSistema configSistema = configSistemaRepository.findFirstByOrderByIdAsc().orElse(null);
        if (configSistema == null) {
            // Autocuración: crear configuración por defecto si la tabla está vacía
            configSistema = new ConfigSistema();
            configSistema.setMargenEncendido(15);
            configSistema.setTiempoMinimoDesperdicio(30);
            configSistema.setActivo(true);
            configSistema = configSistemaRepository.save(configSistema);
        }

        System.out.println("MONITOREO [Aula: " + aula.getCodigo() + 
            "] | Hora actual: " + horaActual + 
            " | Dia actual (1=L, 7=D): " + diaActual + 
            " | hayClase: " + hayClase + 
            " | Tiene Equipo: " + (equipo != null) + 
            " | Estado AC: " + (equipo != null ? equipo.getEstado() : "N/A") + 
            " | Registro Activo: " + (estado != null ? estado.getEstado() : "N/A") +
            " | Margen Config: " + configSistema.getMargenEncendido() + "m"
        );

        if (configSistema == null || estado == null || equipo == null) {
            return;
        }

        List<HorarioAcademico> horarios = horarioAcademicoRepository.findByAulaId(aula.getId());
        List<HorarioAcademico> horariosHoy = horarios.stream()
                .filter(h -> h.getDiaSemana() == diaActual)
                .toList();

        if (horariosHoy.isEmpty() && estado.getEstado() == Estado.ENCENDIDO) {
            System.out.println("MONITOREO [Aula: " + aula.getCodigo() + "] -> Generando alerta 'Desperdicio' (Sin clases hoy y equipo encendido)");
            alertaService.generarAlerta(aula, "Desperdicio");
            return;
        }

        if (estado.getEstado() == Estado.APAGADO) {
            if (hayClase) {
                System.out.println("MONITOREO [Aula: " + aula.getCodigo() + "] -> Generando alerta 'Falta Climatización' (Hay clase activa y equipo apagado)");
                alertaService.generarAlerta(aula, "Falta Climatización");
            }
            else {
                if (proximaClase.isPresent()) {

                    Duration faltante = Duration.between(horaActual, proximaClase.get().getHoraInicio());

                    if (faltante.toMinutes() <= configSistema.getMargenEncendido()) {
                        System.out.println("MONITOREO [Aula: " + aula.getCodigo() + "] -> Generando alerta 'Falta Climatización' (Próxima clase inicia en " + faltante.toMinutes() + " mins)");
                        alertaService.generarAlerta(aula, "Falta Climatización");

                    }
                }
            }
        }

        Optional<HorarioAcademico> claseAnterior = horarioAcademicoRepository.findClaseAnterior(aula, diaActual, horaActual).stream().findFirst();

        if (claseAnterior.isPresent()) {
            LocalTime finClase = claseAnterior.get().getHoraFin();
            LocalTime proximoInicio = proximaClase.map(HorarioAcademico::getHoraInicio).orElse(null);

            boolean esDesperdicio = false;
            if (proximoInicio != null) {
                // Entre clases
                Duration bloqueDesperdicio = Duration.between(finClase, proximoInicio);
                esDesperdicio = !hayClase && estado.getEstado() == Estado.ENCENDIDO 
                        && bloqueDesperdicio.toMinutes() >= configSistema.getTiempoMinimoDesperdicio();
            } else {
                // Después de la última clase del día
                Duration tiempoDesdeFin = Duration.between(finClase, horaActual);
                esDesperdicio = estado.getEstado() == Estado.ENCENDIDO 
                        && tiempoDesdeFin.toMinutes() >= configSistema.getTiempoMinimoDesperdicio();
            }

            if (esDesperdicio) {
                System.out.println("MONITOREO [Aula: " + aula.getCodigo() + "] -> Generando alerta 'Desperdicio' (Equipo encendido fuera de clase)");
                alertaService.generarAlerta(aula, "Desperdicio");
            }
        }

    }
}
