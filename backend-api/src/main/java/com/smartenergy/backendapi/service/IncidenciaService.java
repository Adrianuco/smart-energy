package com.smartenergy.backendapi.service;

import com.smartenergy.backendapi.model.Incidencia;
import com.smartenergy.backendapi.repository.IIncidenciaRepository;
import org.springframework.stereotype.Service;

@Service
public class IncidenciaService extends BaseService<Incidencia, IIncidenciaRepository> {

    public IncidenciaService(IIncidenciaRepository repo) {
        super(repo);
    }
}
