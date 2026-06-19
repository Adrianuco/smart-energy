package com.smartenergy.backendapi.controller;

import com.smartenergy.backendapi.dto.DashboardDTO;
import com.smartenergy.backendapi.model.EstadoAlerta;
import com.smartenergy.backendapi.model.RegistroOperativo;
import com.smartenergy.backendapi.repository.RegistroOperativoRepository;
import com.smartenergy.backendapi.service.AlertaService;
import com.smartenergy.backendapi.service.IncidenciaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private final AlertaService alertaService;
    private final IncidenciaService incidenciaService;
    private final RegistroOperativoRepository registroRepository;

    public DashboardController(
            AlertaService alertaService,
            IncidenciaService incidenciaService,
            RegistroOperativoRepository registroRepository
    ) {
        this.alertaService = alertaService;
        this.incidenciaService = incidenciaService;
        this.registroRepository = registroRepository;
    }

    @GetMapping
    public ResponseEntity<DashboardDTO> dashboard() {

        DashboardDTO dto = new DashboardDTO();

        dto.setAlertasActivas(
                alertaService.findAll()
                        .stream()
                        .filter(a -> a.getEstado() == EstadoAlerta.PENDIENTE)
                        .count()
        );

        dto.setIncidencias(
                incidenciaService.findAll().size()
        );

        dto.setConsumoTotal(
                registroRepository.findAll()
                        .stream()
                        .mapToDouble(RegistroOperativo::getConsumo)
                        .sum()
        );

        return ResponseEntity.ok(dto);
    }
}
