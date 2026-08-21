package com.smartenergy.backendapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "Usuario")
public abstract class Usuario extends BaseEntity {
    private boolean activo;
    private String nombre;
    private String apellido;
    private String cif;
    private String password;

}
