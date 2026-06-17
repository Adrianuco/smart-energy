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
@Table(name = "Incidencia")
public class Incidencia extends BaseEntity{
    private String descripcion;
    private LocalDateTime fechaHora;
    private String tipoIncidencia;
    @ManyToOne
    @JoinColumn(name = "aula_id")
    private Aula aula;
}
