package com.smartenergy.backendapi.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class LoginResponse {
    private UUID id;
    private String nombre;
    private String apellido;
    private String rol;
}
