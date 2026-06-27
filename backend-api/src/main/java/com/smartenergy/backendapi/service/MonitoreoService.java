package com.smartenergy.backendapi.service;


import com.smartenergy.backendapi.model.*;
import com.smartenergy.backendapi.repository.AulaRepository;
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

    public MonitoreoService(HorarioAcademicoRepository horarioAcademicoRepository, RegistroOperativoRepository registroOperativoRepository, IConfigSistemaRepository configSistemaRepository, AulaRepository aulaRepository, AlertaService alertaService) {
        this.horarioAcademicoRepository = horarioAcademicoRepository;
        this.registroOperativoRepository = registroOperativoRepository;
        this.configSistemaRepository = configSistemaRepository;
        this.aulaRepository = aulaRepository;
        this.alertaService = alertaService;
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
        Optional<HorarioAcademico> proximaClase = horarioAcademicoRepository.findProximaClase(aula, diaActual, horaActual);
        boolean hayClase = horarioAcademicoRepository.hayClase(aula, diaActual, horaActual);

        RegistroOperativo estado = registroOperativoRepository.findByEquipoAndFinIsNull(aula.getEquipo()).orElse(null);
        ConfigSistema configSistema = configSistemaRepository.findFirstByOrderByIdAsc().orElse(null);

        if (configSistema == null || estado == null) {
            return;
        }

        if (estado.getEstado() == Estado.APAGADO) {
            if (hayClase) {
                alertaService.generarAlerta(aula, "Falta Climatización");
            }
            else {
                if (proximaClase.isPresent()) {

                    Duration faltante = Duration.between(horaActual, proximaClase.get().getHoraInicio());

                    if (faltante.toMinutes() <= configSistema.getMargenEncendido()) {
                        alertaService.generarAlerta(aula, "Falta Climatización");

                    }
                }
            }
        }

        Optional<HorarioAcademico> claseAnterior = horarioAcademicoRepository.findClaseAnterior(aula, diaActual, horaActual);


        if (proximaClase.isPresent() && claseAnterior.isPresent()) {
            Duration bloqueDesperdicio = Duration.between(claseAnterior.get().getHoraFin(), proximaClase.get().getHoraInicio());
            if(!hayClase && estado.getEstado() == Estado.ENCENDIDO && bloqueDesperdicio.toMinutes() >= configSistema.getTiempoMinimoDesperdicio()) {
                alertaService.generarAlerta(aula, "Desperdicio");
            }
        }

    }
}
