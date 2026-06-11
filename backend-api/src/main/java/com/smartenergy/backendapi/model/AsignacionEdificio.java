package com.smartenergy.backendapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.web.JsonPath;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "AsignacionEdificio")
public class AsignacionEdificio extends BaseEntity {
    private boolean activo;
    private LocalDate fechaAsignacion;

    @ManyToOne
    @JoinColumn(name = "logistica_id")
    private ApoyoLogistica apoyoLogistica;

    @ManyToOne
    @JoinColumn(name = "edificio_id")
    private Edificio edificio;
}
