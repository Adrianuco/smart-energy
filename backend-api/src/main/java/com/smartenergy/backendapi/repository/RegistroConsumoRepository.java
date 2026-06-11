package com.smartenergy.backendapi.repository;

import com.smartenergy.backendapi.model.RegistroConsumo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RegistroConsumoRepository extends JpaRepository<RegistroConsumo, UUID> {
}
