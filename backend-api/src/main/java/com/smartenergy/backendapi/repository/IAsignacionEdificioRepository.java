package com.smartenergy.backendapi.repository;

import com.smartenergy.backendapi.model.AsignacionEdificio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IAsignacionEdificioRepository extends JpaRepository<AsignacionEdificio, UUID> {
}
