package com.smartenergy.backendapi.repository;

import com.smartenergy.backendapi.model.Aula;
import com.smartenergy.backendapi.model.HorarioAcademico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface HorarioAcademicoRepository extends JpaRepository<HorarioAcademico, UUID> {

    List<HorarioAcademico> findByAulaId(UUID id);

    // saber si ya existe un horario
    boolean existsByAulaAndDiaSemanaAndHoraInicioAndHoraFin(Aula aula, int diaSemana, LocalTime horaInicio, LocalTime horaFin);

    // saber si en un momento en especifico hay clase
    @Query("""
        SELECT COUNT(h) > 0 FROM HorarioAcademico h
        WHERE h.aula = ?1 AND h.diaSemana = ?2
        AND h.horaInicio <= ?3  AND h.horaFin >= ?3
    """)
    boolean hayClase(Aula aula, int dia, LocalTime hora);

    // retornar horarios proximos segun hora actual
    @Query("""
        SELECT h FROM HorarioAcademico h
        WHERE h.aula = ?1
        AND h.diaSemana = ?2
        AND h.horaInicio >= ?3
        ORDER BY h.horaInicio ASC
    """)
    List<HorarioAcademico> findProximaClase(Aula aula, int dia, LocalTime hora);

    // retornar horarios anteriores segun hora actual
    @Query("""
        SELECT h FROM HorarioAcademico h
        WHERE h.aula = ?1
        AND h.diaSemana = ?2
        AND h.horaFin <= ?3
        ORDER BY h.horaFin DESC
    """)
    List<HorarioAcademico> findClaseAnterior(Aula aula, int dia, LocalTime hora);

}
