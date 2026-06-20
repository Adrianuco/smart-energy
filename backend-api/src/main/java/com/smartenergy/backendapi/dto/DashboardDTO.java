package com.smartenergy.backendapi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DashboardDTO {
    private long alertasActivas;
    private long incidencias;
    private double consumoTotal;
}
