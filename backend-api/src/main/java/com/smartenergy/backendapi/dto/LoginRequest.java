package com.smartenergy.backendapi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    private String cif;
    private String password;
}
