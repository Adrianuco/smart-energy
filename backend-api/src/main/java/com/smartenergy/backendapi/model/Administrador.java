package com.smartenergy.backendapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Administrador")
public class Administrador extends Usuario{

    private String nivelAcceso;
}
