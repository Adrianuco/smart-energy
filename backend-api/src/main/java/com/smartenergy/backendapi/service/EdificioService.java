package com.smartenergy.backendapi.service;

import com.smartenergy.backendapi.model.Edificio;
import com.smartenergy.backendapi.repository.EdificioRepository;
import org.springframework.stereotype.Service;

@Service
public class EdificioService extends BaseService<Edificio, EdificioRepository> {
    protected EdificioService(EdificioRepository repository) {
        super(repository);
    }

}
