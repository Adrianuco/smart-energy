package com.smartenergy.backendapi.repository;

import com.smartenergy.backendapi.model.Edificio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EdificioRepository extends JpaRepository<Edificio, UUID> {
}
