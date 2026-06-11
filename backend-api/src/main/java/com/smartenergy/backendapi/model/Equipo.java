package com.smartenergy.backendapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Equipo")
public class Equipo extends BaseEntity {
    public String modelo;
    public Boolean operativo;
    public double potenciaMinima;
    public double potenciaNominal;
}
