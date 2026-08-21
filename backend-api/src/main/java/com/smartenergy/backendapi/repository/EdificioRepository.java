package com.smartenergy.backendapi.repository;

import com.smartenergy.backendapi.model.Edificio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EdificioRepository extends JpaRepository<Edificio, UUID> {
    Optional<Edificio> findByNombre(String nombre);
}
