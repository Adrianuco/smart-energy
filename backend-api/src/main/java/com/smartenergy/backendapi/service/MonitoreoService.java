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

    // metodo para verificar todas las aulas cada minuto
    @Scheduled(fixedRate = 60000)
    public void monitorear() {
        List<Aula> aulas = aulaRepository.findAll();

        for (Aula aula : aulas) {
            verificarAula(aula);
        }
    }

    // metodo para verificar sin aula tiene alguna anomalia
    public void verificarAula(Aula aula) {

        // obtenemos el dia y fecha del momento actual
        int diaActual = LocalDateTime.now().getDayOfWeek().getValue();
        LocalTime horaActual = LocalTime.now();
        // obtenemmos la proxima clase y verificamos si hay clase en este momento
        Optional<HorarioAcademico> proximaClase = horarioAcademicoRepository.findProximaClase(aula, diaActual, horaActual).stream().findFirst();
        boolean hayClase = horarioAcademicoRepository.hayClase(aula, diaActual, horaActual);

        // intentamos obtener el equipo del aula, en caso de que no tenga, lo dejamos como null
        Equipo equipo = aula.getEquipo();
        if (equipo == null) {
            equipo = equipoRepository.findByAula(aula).orElse(null);
        }

        // inicializamos el estado en null
        RegistroOperativo estado = null;

        // en caso de que si existe el equipo buscamos el registro operativo actual
        if (equipo != null) {
            estado = registroOperativoRepository.findByEquipoAndFinIsNull(equipo).orElse(null);

            // en caso de que no tenga registro operativo, le inicializamos uno
            if (estado == null) {
                registroOperativoService.cambiarEstado(equipo, equipo.getEstado());
                estado = registroOperativoRepository.findByEquipoAndFinIsNull(equipo).orElse(null);
            }
        }

        // obtenemos la configuracion del sistema
        ConfigSistema configSistema = configSistemaRepository.findFirstByOrderByIdAsc().orElse(null);
        if (configSistema == null) {
            // en caso de que aun no exista una configuracion, definimos una por defecto
            configSistema = new ConfigSistema();
            configSistema.setMargenEncendido(10);
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

        if (estado == null) {
            return;
        }

        // obtenemos todos los horarios del aula y luego los filtramos por dia
        List<HorarioAcademico> horariosHoy = horarioAcademicoRepository.findByAulaId(aula.getId()).stream()
                .filter(h -> h.getDiaSemana() == diaActual)
                .toList();

        // caso 1: el aire esta encendido y no existen horarios
        if (horariosHoy.isEmpty() && estado.getEstado() == Estado.ENCENDIDO) {
            System.out.println("MONITOREO [Aula: " + aula.getCodigo() + "] -> Generando alerta 'Desperdicio' (Sin clases hoy y equipo encendido)");
            alertaService.generarAlerta(aula, "Desperdicio");
            return;
        }

        // caso 2: el aire esta apagado
        if (estado.getEstado() == Estado.APAGADO) {
            // 2.1: hay clase, se manda alerta de desperdicio
            if (hayClase) {
                System.out.println("MONITOREO [Aula: " + aula.getCodigo() + "] -> Generando alerta 'Falta Climatización' (Hay clase activa y equipo apagado)");
                alertaService.generarAlerta(aula, "Falta Climatización");
            }
            else {
                if (proximaClase.isPresent()) {

                    Duration faltante = Duration.between(horaActual, proximaClase.get().getHoraInicio());

            // 2.2: viene clase y estamos dentro del margen de encendido, se manda alerta de falta de climatizacion
                    if (faltante.toMinutes() <= configSistema.getMargenEncendido()) {
                        System.out.println("MONITOREO [Aula: " + aula.getCodigo() + "] -> Generando alerta 'Falta Climatización' (Próxima clase inicia en " + faltante.toMinutes() + " mins)");
                        alertaService.generarAlerta(aula, "Falta Climatización");

                    }
                }
            }
        }

        // buscamos la clase anterior de esa aula
        Optional<HorarioAcademico> claseAnterior = horarioAcademicoRepository.findClaseAnterior(aula, diaActual, horaActual).stream().findFirst();

        // caso 3: aire encendido fuera de clase
        if (estado.getEstado() == Estado.ENCENDIDO && !hayClase) {
            boolean dentroDeMargen = false;
            if (proximaClase.isPresent()) {
                Duration tiempoHastaInicio = Duration.between(horaActual, proximaClase.get().getHoraInicio());
                // hay una clase proxima, no se genera alerta
                if (tiempoHastaInicio.toMinutes() <= configSistema.getMargenEncendido()) {
                    dentroDeMargen = true;
                }
            }

            if (!dentroDeMargen) {
                boolean esDesperdicio = false;
                if (claseAnterior.isPresent()) {
                    Duration tiempoDesdeFin = Duration.between(claseAnterior.get().getHoraFin(), horaActual);
                    // verificar que sea mayor al minimo del tiempo de desperdicio
                    if (tiempoDesdeFin.toMinutes() >= configSistema.getTiempoMinimoDesperdicio()) {
                        esDesperdicio = true;
                    }
                } else {
                    // No hay clase anterior hoy y estamos fuera del margen de la próxima clase
                    esDesperdicio = true;
                }

                if (esDesperdicio) {
                    System.out.println("MONITOREO [Aula: " + aula.getCodigo() + "] -> Generando alerta 'Desperdicio' (Equipo encendido fuera de clase)");
                    alertaService.generarAlerta(aula, "Desperdicio");
                }
            }
        }

    }
}
