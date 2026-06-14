package com.smartenergy.backendapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    private double consumo;
    private LocalDate fecha;
    private LocalTime horaFin;
    private LocalTime horaInicio;

    @ManyToOne
    @JoinColumn(name = "equipo_id")
    private Equipo equipo;
}
