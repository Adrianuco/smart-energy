package com.smartenergy.backendapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name="Alerta")
public class Alerta extends BaseEntity{
    private boolean atendida;
    private String tipoAlerta;
    private String estado;
    @ManyToOne
    @JoinColumn(name = "aula_id")
    private Aula aula;
    private LocalDateTime fechaHora;
}
