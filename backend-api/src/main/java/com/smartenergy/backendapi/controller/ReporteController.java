package com.smartenergy.backendapi.controller;

import com.smartenergy.backendapi.dto.ConsumoEdificioDTO;
import com.smartenergy.backendapi.service.ReporteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/reportes")
public class ReporteController {

    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @GetMapping("/consumo-historico/{id}")
    public ResponseEntity<java.util.List<Double>> getConsumoHistorico(
            @PathVariable UUID id,
            @RequestParam String periodo
    ) {
        return ResponseEntity.ok(reporteService.obtenerConsumoHistorico(id, periodo));
    }
}
