package com.smartenergy.backendapi.dto;

import com.smartenergy.backendapi.model.Aula;
import com.smartenergy.backendapi.model.Equipo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AsignacionEquiposRequest {
    private List<Aula> aulas;
    private Equipo modelo;
}
