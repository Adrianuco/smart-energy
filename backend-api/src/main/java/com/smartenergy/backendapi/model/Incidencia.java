package com.smartenergy.backendapi.model;

import jakarta.persistence.*;
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
    @Enumerated(EnumType.STRING)
    private EstadoAlerta estado;
    @ManyToOne
    @JoinColumn(name = "aula_id")
    private Aula aula;
}
