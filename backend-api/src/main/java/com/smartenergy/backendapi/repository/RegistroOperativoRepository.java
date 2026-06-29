package com.smartenergy.backendapi.repository;

import com.smartenergy.backendapi.model.Equipo;
import com.smartenergy.backendapi.model.RegistroOperativo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RegistroOperativoRepository extends JpaRepository<RegistroOperativo, UUID> {

    // buscar registro operativo actual
    Optional<RegistroOperativo> findByEquipoAndFinIsNull(Equipo equipo);

    // busca el registro operativo mas antiguo
    Optional<RegistroOperativo> findFirstByEquipoOrderByInicioAsc(Equipo equipo);

    // retorna todos los registros operativos en el rango desde start hasta end
    @Query(
            "SELECT r FROM RegistroOperativo r WHERE r.inicio < :end AND (r.fin IS NULL OR r.fin > :start)"
    )
    List<RegistroOperativo> findOverlapping(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
