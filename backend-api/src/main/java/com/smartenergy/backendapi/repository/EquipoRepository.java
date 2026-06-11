package com.smartenergy.backendapi.repository;

import com.smartenergy.backendapi.model.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EquipoRepository extends JpaRepository<Equipo, UUID> {
}
