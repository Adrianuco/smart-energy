package com.smartenergy.backendapi.repository;

import com.smartenergy.backendapi.model.HorarioAcademico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HorarioAcademicoRepository extends JpaRepository<HorarioAcademico, UUID> {
}
