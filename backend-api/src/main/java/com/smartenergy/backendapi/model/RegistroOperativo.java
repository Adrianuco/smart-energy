package com.smartenergy.backendapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "RegistroOperativo")
public class RegistroOperativo extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private Estado estado;

    private double consumo;
    private LocalDate fecha;
    private LocalTime horaFin;
    private LocalTime horaInicio;

    @ManyToOne
    @JoinColumn(name = "equipo_id")
    private Equipo equipo;
}
