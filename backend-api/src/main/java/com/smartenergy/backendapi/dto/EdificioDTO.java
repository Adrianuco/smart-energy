package com.smartenergy.backendapi.dto;

import com.smartenergy.backendapi.model.Aula;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class EdificioDTO {
    private UUID id;
    private String nombre;
    private double consumo;
    private double consumoEsperado;
    private double ahorro;
    private String estado;
    private List<Aula> aulas;
}
