package com.smartenergy.backendapi.repository;

import com.smartenergy.backendapi.model.Incidencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IIncidenciaRepository extends JpaRepository<Incidencia, UUID> {
}
