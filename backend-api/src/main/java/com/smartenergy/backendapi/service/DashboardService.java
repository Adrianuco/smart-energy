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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


import static java.util.Locale.ENGLISH;

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

    // metodo para obtener el dashboard
    public DashboardDTO getDashboard() {
        DashboardDTO dto = new DashboardDTO();

        // se calculan los datos de consumo y ahorro
        double consumoActual = calculoService.calcularConsumoHoy();
        double consumoEsperado = calculoService.calcularConsumoEsperadoTotalHoy();
        double kwhAhorrados = Math.max(0.0, consumoEsperado - consumoActual);

        dto.setConsumoActual(consumoActual);
        // se calcula el ahorro hasta ese momento en el tiempo
        dto.setAhorro(calculoService.calcularAhorro());
        dto.setKwhAhorrados(kwhAhorrados);

        // se cuentan todas las alertas e incidencias filtradas por pendiente
        dto.setAlertasActivas(alertaService.findAll().stream().filter(a -> a.getEstado() == EstadoAlerta.PENDIENTE).count());
        dto.setIncidencias(incidenciaService.findAll().stream().filter(a -> a.getEstado() == EstadoAlerta.PENDIENTE).count());

        // se cuentan todos los edificios y equipos
        dto.setEdificios(edificioRepository.count());
        dto.setEquiposActivos(equipoRepository.count());

        // se calcula el consumo para las ultimas horas
        dto.setConsumoUltimasHoras(calculoService.calcularConsumoUltimasHoras());

        // se calculan los labels de las horas para el grafico del dashboard
        LocalDateTime now = LocalDateTime.now();
        List<String> horas = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ha", ENGLISH);
        for (int i = 6; i >= 0; i--) {
            horas.add(now.minusHours(i).format(formatter).toLowerCase());
        }
        dto.setHoras(horas);

        return dto;
    }
}
