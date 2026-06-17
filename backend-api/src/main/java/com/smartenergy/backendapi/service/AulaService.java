package com.smartenergy.backendapi.service;

import com.smartenergy.backendapi.model.Aula;
import com.smartenergy.backendapi.repository.AulaRepository;
import org.springframework.stereotype.Service;

@Service
public class AulaService extends BaseService<Aula, AulaRepository> {
    protected AulaService(AulaRepository repository) {
        super(repository);
    }

    public Aula findByCodigo(String codigo){
        return repo.findByCodigo(codigo).orElseThrow(() -> new RuntimeException("No existe el aula: " + codigo));
    }
}
