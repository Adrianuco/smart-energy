package com.smartenergy.backendapi.repository;

import com.smartenergy.backendapi.model.Alerta;
import com.smartenergy.backendapi.model.Aula;
import com.smartenergy.backendapi.model.EstadoAlerta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IAlertaRepository extends JpaRepository<Alerta, UUID> {

    // saber si existe una alerta segun el aula, tipo y estado
    boolean existsByAulaAndTipoAlertaAndEstado(Aula aula, String tipoAlerta, EstadoAlerta estadoAlerta);

    // saber si existe una alerta por id de un edificio y estado
    boolean existsByAulaEdificioIdAndEstado(UUID edificioId, EstadoAlerta estado);
}
