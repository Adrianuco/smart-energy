package com.smartenergy.backendapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

@Getter
@Setter
@Entity
@Table(name = "Equipo")
public class Equipo extends BaseEntity {
    private String marca;
    private String modelo;
    private int btu;
    private String eficiencia;
    private Boolean operativo;
    private double potenciaMinima;
    private double potenciaNominal;

    @Enumerated(EnumType.STRING)
    private Estado estado = Estado.APAGADO;

    @OneToOne
    @JoinColumn(name = "aula_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Aula aula;
}
