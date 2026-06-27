package com.smartenergy.backendapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ConfigSistema")
public class ConfigSistema extends BaseEntity {
    private int margenEncendido = 10;
    private int tiempoMinimoDesperdicio = 60;
    private boolean activo = true;
}
