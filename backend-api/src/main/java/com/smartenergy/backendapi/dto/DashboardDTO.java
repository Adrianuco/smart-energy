package com.smartenergy.backendapi.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DashboardDTO {

    private double consumoActual;
    private double ahorro;
    private double kwhAhorrados;
    private long alertasActivas;
    private long edificios;
    private long equiposActivos;
    private long incidencias;
    private List<Double> consumoUltimasHoras;
    private List<String> horas;

}
