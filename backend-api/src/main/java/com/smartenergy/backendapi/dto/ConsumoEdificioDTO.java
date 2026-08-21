package com.smartenergy.backendapi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsumoEdificioDTO {
    private String edificio;
    private double consumo;
}
