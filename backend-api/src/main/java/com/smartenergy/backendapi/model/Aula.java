package com.smartenergy.backendapi.model;

import jakarta.persistence.*;
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
    private Edificio edificio;
    private String codigo;

}
