package com.smartenergy.backendapi.service;

import com.smartenergy.backendapi.dto.ConsumoEdificioDTO;
import com.smartenergy.backendapi.model.RegistroOperativo;
import com.smartenergy.backendapi.repository.EdificioRepository;
import com.smartenergy.backendapi.repository.RegistroOperativoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ReporteService {

    private final RegistroOperativoRepository registroRepository;
    private final CalculoService calculoService;

    public ReporteService(
            RegistroOperativoRepository registroRepository,
            CalculoService calculoService
    ) {
        this.registroRepository = registroRepository;
        this.calculoService = calculoService;
    }

    public List<Double> obtenerConsumoHistorico(UUID id, String periodo) {
        List<Double> consumos = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        if ("Hoy".equalsIgnoreCase(periodo)) {
            // ultimas 7 horas
            for (int i = 6; i >= 0; i--) {
                LocalDateTime start = now.minusHours(i).withMinute(0).withSecond(0).withNano(0);
                LocalDateTime end = start.plusHours(1);
                double sum = registroRepository.findOverlapping(start, end).stream()
                        .filter(r -> perteneceAEdificio(r, id))
                        .mapToDouble(r -> calculoService.obtenerConsumoEnIntervalo(r, start, end))
                        .sum();
                consumos.add(sum);
            }
        } else if ("Semana".equalsIgnoreCase(periodo)) {
            // ultimos 7 dias
            for (int i = 6; i >= 0; i--) {
                LocalDateTime start = now.minusDays(i).withHour(0).withMinute(0).withSecond(0).withNano(0);
                LocalDateTime end = start.plusDays(1);
                double sum = registroRepository.findOverlapping(start, end).stream()
                        .filter(r -> perteneceAEdificio(r, id))
                        .mapToDouble(r -> calculoService.obtenerConsumoEnIntervalo(r, start, end))
                        .sum();
                consumos.add(sum);
            }
        } else {
            // ultimas 4 semanas
            for (int i = 3; i >= 0; i--) {
                LocalDateTime start = now.minusWeeks(i).withHour(0).withMinute(0).withSecond(0).withNano(0);
                LocalDateTime end = start.plusWeeks(1);
                double sum = registroRepository.findOverlapping(start, end).stream()
                        .filter(r -> perteneceAEdificio(r, id))
                        .mapToDouble(r -> calculoService.obtenerConsumoEnIntervalo(r, start, end))
                        .sum();
                consumos.add(sum);
            }
        }
        return consumos;
    }

    private boolean perteneceAEdificio(RegistroOperativo r, UUID edificioId){

        return r.getEquipo() != null &&
                r.getEquipo().getAula() != null &&
                r.getEquipo().getAula().getEdificio() != null &&
                r.getEquipo().getAula().getEdificio().getId().equals(edificioId);
    }
}
