package com.smartenergy.backendapi.repository;

import com.smartenergy.backendapi.model.ApoyoLogistica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IApoyoLogisticaRepository extends JpaRepository<ApoyoLogistica, UUID> {
    Optional<ApoyoLogistica> findByCif(String cif);
}
