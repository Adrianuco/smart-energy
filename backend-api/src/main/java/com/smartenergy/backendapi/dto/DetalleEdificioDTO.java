package com.smartenergy.backendapi.dto;

import com.smartenergy.backendapi.model.Aula;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DetalleEdificioDTO {
    private String nombre;
    private double consumo;
    private List<Aula> aulas;
}
