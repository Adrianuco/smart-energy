package com.smartenergy.backendapi.repository;

import com.smartenergy.backendapi.model.ApoyoLogistica;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IApoyoLogisticaRepository extends JpaRepository<ApoyoLogistica, UUID> {
}
