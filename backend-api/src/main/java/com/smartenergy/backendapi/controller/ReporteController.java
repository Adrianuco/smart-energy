package com.smartenergy.backendapi.controller;

import com.smartenergy.backendapi.dto.ConsumoEdificioDTO;
import com.smartenergy.backendapi.model.RegistroOperativo;
import com.smartenergy.backendapi.repository.EdificioRepository;
import com.smartenergy.backendapi.repository.RegistroOperativoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reportes")
public class ReporteController {

    private final RegistroOperativoRepository registroRepository;
    private final EdificioRepository edificioRepository;

    public ReporteController(
            RegistroOperativoRepository registroRepository,
            EdificioRepository edificioRepository
    ) {
        this.registroRepository = registroRepository;
        this.edificioRepository = edificioRepository;
    }

    @GetMapping("/consumo-total")
    public ResponseEntity<Double> consumoTotal() {

        double total =
                registroRepository.findAll()
                        .stream()
                        .mapToDouble(RegistroOperativo::getConsumo)
                        .sum();

        return ResponseEntity.ok(total);
    }

    @GetMapping("/consumo-edificio/{id}")
    public ResponseEntity<ConsumoEdificioDTO> consumoEdificio(
            @PathVariable UUID id
    ) {

        double consumo =
                registroRepository.findAll()
                        .stream()
                        .filter(r ->
                                r.getEquipo()
                                        .getAula()
                                        .getEdificio()
                                        .getId()
                                        .equals(id))
                        .mapToDouble(RegistroOperativo::getConsumo)
                        .sum();

        String nombreEdificio =
                edificioRepository.findById(id)
                        .orElseThrow()
                        .getNombre();

        ConsumoEdificioDTO dto = new ConsumoEdificioDTO();

        dto.setEdificio(nombreEdificio);
        dto.setConsumo(consumo);

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/consumo-mensual/{id}")
    public ResponseEntity<Map<Integer, Double>> consumoMensual(
            @PathVariable UUID id
    ) {
        Map<Integer, Double> resultado =
                registroRepository.findAll()
                        .stream()
                        .filter(r ->
                                r.getEquipo()
                                        .getAula()
                                        .getEdificio()
                                        .getId()
                                        .equals(id))
                        .collect(Collectors.groupingBy(
                                r -> r.getInicio().getMonthValue(),
                                Collectors.summingDouble(
                                        RegistroOperativo::getConsumo
                                )
                        ));

        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/consumo-historico/{id}")
    public ResponseEntity<java.util.List<Double>> getConsumoHistorico(
            @PathVariable UUID id,
            @RequestParam String periodo
    ) {
        java.util.List<Double> consumos = new java.util.ArrayList<>();
        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        if ("Hoy".equalsIgnoreCase(periodo)) {
            // Last 7 hours
            for (int i = 6; i >= 0; i--) {
                java.time.LocalDateTime start = now.minusHours(i).withMinute(0).withSecond(0).withNano(0);
                java.time.LocalDateTime end = start.plusHours(1);
                double sum = registroRepository.findByInicioBetween(start, end).stream()
                        .filter(r -> r.getEquipo() != null && r.getEquipo().getAula() != null && r.getEquipo().getAula().getEdificio() != null && r.getEquipo().getAula().getEdificio().getId().equals(id))
                        .mapToDouble(RegistroOperativo::getConsumo)
                        .sum();
                consumos.add(sum);
            }
        } else if ("Semana".equalsIgnoreCase(periodo)) {
            // Last 7 days
            for (int i = 6; i >= 0; i--) {
                java.time.LocalDateTime start = now.minusDays(i).withHour(0).withMinute(0).withSecond(0).withNano(0);
                java.time.LocalDateTime end = start.plusDays(1);
                double sum = registroRepository.findByInicioBetween(start, end).stream()
                        .filter(r -> r.getEquipo() != null && r.getEquipo().getAula() != null && r.getEquipo().getAula().getEdificio() != null && r.getEquipo().getAula().getEdificio().getId().equals(id))
                        .mapToDouble(RegistroOperativo::getConsumo)
                        .sum();
                consumos.add(sum);
            }
        } else {
            // Month: Last 4 weeks
            for (int i = 3; i >= 0; i--) {
                java.time.LocalDateTime start = now.minusWeeks(i).withHour(0).withMinute(0).withSecond(0).withNano(0);
                java.time.LocalDateTime end = start.plusWeeks(1);
                double sum = registroRepository.findByInicioBetween(start, end).stream()
                        .filter(r -> r.getEquipo() != null && r.getEquipo().getAula() != null && r.getEquipo().getAula().getEdificio() != null && r.getEquipo().getAula().getEdificio().getId().equals(id))
                        .mapToDouble(RegistroOperativo::getConsumo)
                        .sum();
                consumos.add(sum);
            }
        }
        return ResponseEntity.ok(consumos);
    }
}
