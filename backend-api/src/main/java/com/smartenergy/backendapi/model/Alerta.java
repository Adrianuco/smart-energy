package com.smartenergy.backendapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name="Alerta")
public class Alerta extends BaseEntity{
    private String tipoAlerta;
    @Enumerated(EnumType.STRING)
    private EstadoAlerta estado;
    @ManyToOne
    @JoinColumn(name = "aula_id")
    private Aula aula;
    private LocalDateTime fechaHora;
}
