package com.smartenergy.backendapi.service;

import com.smartenergy.backendapi.model.*;
import com.smartenergy.backendapi.repository.AulaRepository;
import com.smartenergy.backendapi.repository.HorarioAcademicoRepository;
import com.smartenergy.backendapi.repository.RegistroOperativoRepository;
import com.smartenergy.backendapi.repository.IConfigSistemaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CalculoService {

    private final RegistroOperativoRepository registroOperativoRepository;
    private final AulaRepository aulaRepository;
    private final HorarioAcademicoRepository horarioAcademicoRepository;
    private final IConfigSistemaRepository configSistemaRepository;

    public CalculoService(RegistroOperativoRepository registroOperativoRepository, AulaRepository aulaRepository, HorarioAcademicoRepository horarioAcademicoRepository, IConfigSistemaRepository configSistemaRepository) {
        this.registroOperativoRepository = registroOperativoRepository;
        this.aulaRepository = aulaRepository;
        this.horarioAcademicoRepository = horarioAcademicoRepository;
        this.configSistemaRepository = configSistemaRepository;
    }

    public double obtenerConsumoDeRegistro(RegistroOperativo r) {
        if (r.getEstado() == Estado.APAGADO) {
            return 0.0;
        }
        Equipo equipo = r.getEquipo();
        if (equipo == null) {
            return 0.0;
        }
        LocalDateTime finTemporal = r.getFin() != null ? r.getFin() : LocalDateTime.now();
        long minutosDiferencia = ChronoUnit.MINUTES.between(r.getInicio(), finTemporal);
        double tiempoConsumo = minutosDiferencia / 60.0;
        return (((equipo.getPotenciaMinima() + equipo.getPotenciaNominal()) / 2) * tiempoConsumo) / 1000.0;
    }

    public void calcularConsumoRegistro(RegistroOperativo registroOperativo) {
        registroOperativo.setConsumo(obtenerConsumoDeRegistro(registroOperativo));
    }

    public double calcularConsumoEdificio(UUID edificioId) {
        LocalDateTime inicio = LocalDate.now().atStartOfDay();
        LocalDateTime fin = LocalDateTime.now();

        List<RegistroOperativo> registros = registroOperativoRepository.findByInicioBetween(inicio, fin);

        return registros.stream().filter(r ->
                        r.getEquipo() != null &&
                        r.getEquipo().getAula() != null &&
                        r.getEquipo().getAula().getEdificio() != null &&
                        r.getEquipo().getAula().getEdificio().getId().equals(edificioId))
                .mapToDouble(this::obtenerConsumoDeRegistro)
                .sum();
    }

    public double calcularConsumoHoy() {
        LocalDateTime inicio = LocalDate.now().atStartOfDay();
        LocalDateTime fin = LocalDateTime.now();

        return registroOperativoRepository.findByInicioBetween(inicio, fin)
                .stream()
                .mapToDouble(this::obtenerConsumoDeRegistro)
                .sum();
    }

    public List<Double> calcularConsumoUltimasHoras() {
        LocalDateTime now = LocalDateTime.now();
        List<Double> consumos = new ArrayList<>();
        for (int i = 6; i >= 0; i--) {
            LocalDateTime startOfHour = now.minusHours(i).withMinute(0).withSecond(0).withNano(0);
            LocalDateTime endOfHour = startOfHour.plusHours(1);

            double sum = registroOperativoRepository.findByInicioBetween(startOfHour, endOfHour)
                    .stream()
                    .mapToDouble(this::obtenerConsumoDeRegistro)
                    .sum();
            consumos.add(sum);
        }
        return consumos;
    }

    public double calcularAhorro() {
        double consumoActual = calcularConsumoHoy();
        if (consumoActual == 0) {
            return 0.0;
        }

        double consumoEsperadoTotal = aulaRepository.findAll().stream()
                .mapToDouble(aula -> {
                    try {
                        return calcularConsumoEsperadoAula(aula.getId());
                    } catch (Exception e) {
                        return 0.0;
                    }
                })
                .sum();

        if (consumoEsperadoTotal <= 0) {
            return 0.0;
        }

        return (consumoEsperadoTotal - consumoActual) / consumoEsperadoTotal;

    }

    public double calcularConsumoEsperadoAula(UUID aulaId){

        Aula aula = aulaRepository.findById(aulaId)
                .orElseThrow();


        List<HorarioAcademico> horarios =
                horarioAcademicoRepository.findByAulaId(aulaId);

        int diaSemanaActual = LocalDate.now().getDayOfWeek().getValue();
        LocalTime now = LocalTime.now();

        ConfigSistema config = configSistemaRepository.findFirstByOrderByIdAsc().orElse(null);
        int margenEncendido = (config != null) ? config.getMargenEncendido() : 0;

        List<HorarioAcademico> horariosHoy = horarios.stream()
                .filter(h -> h.getDiaSemana() == diaSemanaActual)
                .toList();

        if (horariosHoy.isEmpty()) {
            return 0.0;
        }

        LocalTime horaInicioMinima = null;
        LocalTime horaFinMaxima = null;

        for (HorarioAcademico h : horariosHoy) {
            LocalTime horaInicioAjustada = h.getHoraInicio().minusMinutes(margenEncendido);
            if (horaInicioAjustada.isAfter(h.getHoraInicio())) {
                horaInicioAjustada = LocalTime.MIN;
            }
            if (horaInicioMinima == null || horaInicioAjustada.isBefore(horaInicioMinima)) {
                horaInicioMinima = horaInicioAjustada;
            }
            if (horaFinMaxima == null || h.getHoraFin().isAfter(horaFinMaxima)) {
                horaFinMaxima = h.getHoraFin();
            }
        }

        if (horaInicioMinima == null || now.isBefore(horaInicioMinima)) {
            return 0.0;
        }

        LocalTime finCalculo = now.isBefore(horaFinMaxima) ? now : horaFinMaxima;
        long minutos = ChronoUnit.MINUTES.between(horaInicioMinima, finCalculo);
        double horasTotales = minutos / 60.0;


        Equipo equipo = aula.getEquipo();
        if (equipo == null) {
            return 0.0;
        }

        return (((equipo.getPotenciaNominal() + equipo.getPotenciaMinima()) / 2) * horasTotales) / 1000.0;

    }

    public double calcularConsumoEsperadoDiaCompletoAula(UUID aulaId){
        Aula aula = aulaRepository.findById(aulaId)
                .orElseThrow();

        List<HorarioAcademico> horarios =
                horarioAcademicoRepository.findByAulaId(aulaId);

        int diaSemanaActual = LocalDate.now().getDayOfWeek().getValue();

        ConfigSistema config = configSistemaRepository.findFirstByOrderByIdAsc().orElse(null);
        int margenEncendido = (config != null) ? config.getMargenEncendido() : 0;

        List<HorarioAcademico> horariosHoy = horarios.stream()
                .filter(h -> h.getDiaSemana() == diaSemanaActual)
                .toList();

        if (horariosHoy.isEmpty()) {
            return 0.0;
        }

        LocalTime horaInicioMinima = null;
        LocalTime horaFinMaxima = null;

        for (HorarioAcademico h : horariosHoy) {
            LocalTime horaInicioAjustada = h.getHoraInicio().minusMinutes(margenEncendido);
            if (horaInicioAjustada.isAfter(h.getHoraInicio())) {
                horaInicioAjustada = LocalTime.MIN;
            }
            if (horaInicioMinima == null || horaInicioAjustada.isBefore(horaInicioMinima)) {
                horaInicioMinima = horaInicioAjustada;
            }
            if (horaFinMaxima == null || h.getHoraFin().isAfter(horaFinMaxima)) {
                horaFinMaxima = h.getHoraFin();
            }
        }

        if (horaInicioMinima == null || horaFinMaxima == null) {
            return 0.0;
        }

        long minutos = ChronoUnit.MINUTES.between(horaInicioMinima, horaFinMaxima);
        double horasTotales = minutos / 60.0;

        Equipo equipo = aula.getEquipo();
        if (equipo == null) {
            return 0.0;
        }

        return (((equipo.getPotenciaNominal() + equipo.getPotenciaMinima()) / 2) * horasTotales) / 1000.0;
    }

    public double calcularConsumoEsperadoDiaCompletoEdificio(UUID edificioId) {
        return aulaRepository.findByEdificioId(edificioId).stream()
                .mapToDouble(aula -> {
                    try {
                        if (aula.getEquipo() == null) {
                            return 0.0;
                        }
                        return calcularConsumoEsperadoDiaCompletoAula(aula.getId());
                    } catch (Exception e) {
                        return 0.0;
                    }
                })
                .sum();
    }

    public double obtenerConsumoEnIntervalo(RegistroOperativo r, LocalDateTime start, LocalDateTime end) {
        if (r.getEstado() == Estado.APAGADO) {
            return 0.0;
        }
        Equipo equipo = r.getEquipo();
        if (equipo == null) {
            return 0.0;
        }
        LocalDateTime finTemporal = r.getFin() != null ? r.getFin() : LocalDateTime.now();

        // Find overlap
        LocalDateTime overlapStart = r.getInicio().isBefore(start) ? start : r.getInicio();
        LocalDateTime overlapEnd = finTemporal.isAfter(end) ? end : finTemporal;

        if (overlapStart.isBefore(overlapEnd)) {
            long minutosDiferencia = ChronoUnit.MINUTES.between(overlapStart, overlapEnd);
            double tiempoConsumo = minutosDiferencia / 60.0;
            return (((equipo.getPotenciaMinima() + equipo.getPotenciaNominal()) / 2) * tiempoConsumo) / 1000.0;
        }
        return 0.0;
    }

    public double calcularConsumoEsperadoEdificio(UUID edificioId) {
        return aulaRepository.findByEdificioId(edificioId).stream()
                .mapToDouble(aula -> {
                    try {
                        if (aula.getEquipo() == null) {
                            return 0.0;
                        }
                        return calcularConsumoEsperadoAula(aula.getId());
                    } catch (Exception e) {
                        return 0.0;
                    }
                })
                .sum();
    }

    public double calcularAhorroEdificio(UUID edificioId) {
        double consumoActual = calcularConsumoEdificio(edificioId);
        double consumoEsperado = calcularConsumoEsperadoEdificio(edificioId);
        if (consumoEsperado <= 0) {
            return 0.0;
        }
        double diff = (consumoEsperado - consumoActual) / consumoEsperado;
        return Math.max(0.0, diff);
    }
}

