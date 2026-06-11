package com.smartenergy.backendapi.repository;

import com.smartenergy.backendapi.model.Aula;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AulaRepository extends JpaRepository<Aula, UUID> {
}
