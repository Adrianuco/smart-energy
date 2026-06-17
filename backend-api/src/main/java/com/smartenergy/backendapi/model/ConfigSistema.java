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
    private int margenEncendido;
    private int tiempoMinimoDesperdicio;
    private boolean activo;
}
