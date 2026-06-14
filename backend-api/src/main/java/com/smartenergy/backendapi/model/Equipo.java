package com.smartenergy.backendapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Equipo")
public class Equipo extends BaseEntity {
    private String modelo;
    private Boolean operativo;
    private double potenciaMinima;
    private double potenciaNominal;

    @OneToOne
    @JoinColumn(name = "aula_id")
    private Aula aula;
}
