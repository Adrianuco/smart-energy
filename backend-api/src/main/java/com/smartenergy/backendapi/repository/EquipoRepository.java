package com.smartenergy.backendapi.repository;

import com.smartenergy.backendapi.model.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EquipoRepository extends JpaRepository<Equipo, UUID> {
    boolean existsByAula(com.smartenergy.backendapi.model.Aula aula);

    java.util.Optional<Equipo> findByAula(com.smartenergy.backendapi.model.Aula aula);
}
