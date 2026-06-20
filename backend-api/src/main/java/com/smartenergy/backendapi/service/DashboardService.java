package com.smartenergy.backendapi.service;

import com.smartenergy.backendapi.dto.DashboardDTO;
import com.smartenergy.backendapi.model.Estado;
import com.smartenergy.backendapi.model.EstadoAlerta;
import com.smartenergy.backendapi.model.RegistroOperativo;
import com.smartenergy.backendapi.repository.AulaRepository;
import com.smartenergy.backendapi.repository.EdificioRepository;
import com.smartenergy.backendapi.repository.EquipoRepository;
import com.smartenergy.backendapi.repository.RegistroOperativoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {
    private final CalculoService calculoService;
    private final AlertaService alertaService;
    private final IncidenciaService incidenciaService;
    private final EdificioRepository edificioRepository;
    private final EquipoRepository equipoRepository;

    public DashboardService(CalculoService calculoService, AlertaService alertaService, IncidenciaService incidenciaService, EdificioRepository edificioRepository, EquipoRepository equipoRepository) {
        this.calculoService = calculoService;
        this.alertaService = alertaService;
        this.incidenciaService = incidenciaService;
        this.edificioRepository = edificioRepository;
        this.equipoRepository = equipoRepository;
    }

    public DashboardDTO getDashboard() {
        DashboardDTO dto = new DashboardDTO();

        dto.setConsumoActual(calculoService.calcularConsumoHoy());
        dto.setAhorro(calculoService.calcularAhorro());
        dto.setAlertasActivas(alertaService.findAll().stream().filter(a -> a.getEstado() == EstadoAlerta.PENDIENTE).count());
        dto.setEdificios(edificioRepository.count());
        dto.setEquiposActivos(equipoRepository.count());
        dto.setIncidencias(incidenciaService.findAll().stream().filter(a -> a.getEstado() == EstadoAlerta.PENDIENTE).count());
        dto.setConsumoUltimasHoras(calculoService.calcularConsumoUltimasHoras());

        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        java.util.List<String> horas = new java.util.ArrayList<>();
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("ha", java.util.Locale.ENGLISH);
        for (int i = 6; i >= 0; i--) {
            horas.add(now.minusHours(i).format(formatter).toLowerCase());
        }
        dto.setHoras(horas);

        return dto;
    }
}
