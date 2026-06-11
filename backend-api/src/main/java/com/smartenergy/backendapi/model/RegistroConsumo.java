package com.smartenergy.backendapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "RegistroConsumo")
public class RegistroConsumo extends BaseEntity {
    public double consumo;
    public LocalDate fecha;
    public LocalTime horaFin;
    public LocalTime horaInicio;
}
