package com.smartenergy.backendapi.repository;

import com.smartenergy.backendapi.model.RegistroConsumo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RegistroConsumoRepository extends JpaRepository<RegistroConsumo, UUID> {
}
