package com.smartenergy.backendapi.service;

import com.smartenergy.backendapi.model.Equipo;
import com.smartenergy.backendapi.repository.EquipoRepository;
import org.springframework.stereotype.Service;

@Service
public class EquipoService extends BaseService<Equipo, EquipoRepository> {
    protected EquipoService(EquipoRepository repository) {
        super(repository);
    }
}
