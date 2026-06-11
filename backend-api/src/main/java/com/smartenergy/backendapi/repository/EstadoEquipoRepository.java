package com.smartenergy.backendapi.repository;

import com.smartenergy.backendapi.model.EstadoEquipo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EstadoEquipoRepository extends  JpaRepository<EstadoEquipo, UUID> {
}
