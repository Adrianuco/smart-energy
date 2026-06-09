package com.smartenergy.backendapi.service;

import com.smartenergy.backendapi.model.Aula;
import com.smartenergy.backendapi.repository.IAulaRepository;
import org.springframework.stereotype.Service;

@Service
public class AulaService extends BaseService<Aula, IAulaRepository>{
    public AulaService(IAulaRepository repo) {
        super(repo);
    }
}
