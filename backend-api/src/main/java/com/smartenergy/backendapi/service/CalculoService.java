package com.smartenergy.backendapi.service;

import com.smartenergy.backendapi.model.*;
import com.smartenergy.backendapi.repository.AulaRepository;
import com.smartenergy.backendapi.repository.HorarioAcademicoRepository;
import com.smartenergy.backendapi.repository.RegistroOperativoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
public class CalculoService {

    private final RegistroOperativoRepository registroOperativoRepository;
    private final AulaRepository aulaRepository;
    private final HorarioAcademicoRepository horarioAcademicoRepository;

    public CalculoService(RegistroOperativoRepository registroOperativoRepository, AulaRepository aulaRepository, HorarioAcademicoRepository horarioAcademicoRepository) {
        this.registroOperativoRepository = registroOperativoRepository;
        this.aulaRepository = aulaRepository;
        this.horarioAcademicoRepository = horarioAcademicoRepository;
    }

    public void calcularConsumoRegistro(RegistroOperativo registroOperativo) {

        if (registroOperativo.getEstado() == Estado.ENCENDIDO) {
            Equipo equipo = registroOperativo.getEquipo();
            double potenciaMinima = equipo.getPotenciaMinima();
            double potenciaNominal = equipo.getPotenciaNominal();
            long minutosDiferencia = ChronoUnit.MINUTES.between(registroOperativo.getInicio(), registroOperativo.getFin());
            double tiempoConsumo = minutosDiferencia / 60.0;
            double consumo = ((potenciaMinima + potenciaNominal) / 2) * tiempoConsumo;

            registroOperativo.setConsumo(consumo);
        }
        else {
            registroOperativo.setConsumo(0);
        }

    }

    public double calcularConsumoEdificio(UUID edificioId) {
        LocalDateTime inicio = LocalDate.now().atStartOfDay();
        LocalDateTime fin = LocalDate.now().atTime(LocalTime.MAX);

        List<RegistroOperativo> registros = registroOperativoRepository.findByInicioBetween(inicio, fin);

        return registros.stream().filter(r ->
                        r.getEquipo()
                                .getAula()
                                .getEdificio()
                                .getId()
                                .equals(edificioId))
                .mapToDouble(RegistroOperativo::getConsumo)
                .sum();
    }

    public double calcularConsumoHoy() {
        LocalDateTime inicio = LocalDate.now().atStartOfDay();
        LocalDateTime fin = LocalDate.now().atTime(LocalTime.MAX);

        return registroOperativoRepository.findByInicioBetween(inicio, fin)
                .stream()
                .mapToDouble(RegistroOperativo::getConsumo)
                .sum();
    }

    public List<Double> calcularConsumoUltimasHoras() {
        LocalDateTime now = LocalDateTime.now();
        java.util.List<Double> consumos = new java.util.ArrayList<>();
        for (int i = 6; i >= 0; i--) {
            LocalDateTime startOfHour = now.minusHours(i).withMinute(0).withSecond(0).withNano(0);
            LocalDateTime endOfHour = startOfHour.plusHours(1);

            double sum = registroOperativoRepository.findByInicioBetween(startOfHour, endOfHour)
                    .stream()
                    .mapToDouble(RegistroOperativo::getConsumo)
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


        double horasTotales = horarios.stream()
                .mapToDouble(h -> {

                    long minutos =
                            ChronoUnit.MINUTES.between(
                                    h.getHoraInicio(),
                                    h.getHoraFin()
                            );

                    return minutos / 60.0;

                })
                .sum();


        Equipo equipo = aula.getEquipo();


        return ((equipo.getPotenciaNominal() + equipo.getPotenciaMinima()) / 2) * horasTotales;

    }
}

