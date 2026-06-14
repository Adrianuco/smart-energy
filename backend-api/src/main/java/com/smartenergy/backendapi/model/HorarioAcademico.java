package com.smartenergy.backendapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "HorarioAcademico")
public class HorarioAcademico extends BaseEntity {
    private String asignatura;
    private int diaSemana;
    private LocalTime horaFin;
    private LocalTime horaInicio;

    @ManyToOne
    @JoinColumn(name = "aula_id")
    private Aula aula;
}
