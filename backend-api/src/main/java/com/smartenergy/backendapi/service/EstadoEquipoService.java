package com.smartenergy.backendapi.service;

import com.smartenergy.backendapi.model.EstadoEquipo;
import com.smartenergy.backendapi.repository.EstadoEquipoRepository;
import org.springframework.stereotype.Service;

@Service
public class EstadoEquipoService extends BaseService<EstadoEquipo, EstadoEquipoRepository> {
    protected EstadoEquipoService(EstadoEquipoRepository repository) {
        super(repository);
    }
}
