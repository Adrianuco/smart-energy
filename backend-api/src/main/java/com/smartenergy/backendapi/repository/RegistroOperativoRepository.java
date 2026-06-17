package com.smartenergy.backendapi.repository;

import com.smartenergy.backendapi.model.Equipo;
import com.smartenergy.backendapi.model.RegistroOperativo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RegistroOperativoRepository extends JpaRepository<RegistroOperativo, UUID> {

    Optional<RegistroOperativo> findByEquipoAndFinIsNull(Equipo equipo);
}
