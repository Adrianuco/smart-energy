package com.smartenergy.backendapi.repository;

import com.smartenergy.backendapi.model.Aula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AulaRepository extends JpaRepository<Aula, UUID> {

    Optional<Aula> findByCodigo(String codigo);

    Optional<List<Aula>> findByEdificioId(UUID edificioId);

    UUID id(UUID id);
}
