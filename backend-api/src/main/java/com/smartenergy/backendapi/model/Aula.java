package com.smartenergy.backendapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Aula")
public class Aula extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "edificio_id")
    public Edificio edificio;
    public List<HorarioAcademico> horarios;
    public String nombre;
}
