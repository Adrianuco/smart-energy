package com.smartenergy.backendapi.repository;

import com.smartenergy.backendapi.model.Aula;
import com.smartenergy.backendapi.model.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EquipoRepository extends JpaRepository<Equipo, UUID> {
    Optional<Equipo> findByAula(Aula aula);
}
