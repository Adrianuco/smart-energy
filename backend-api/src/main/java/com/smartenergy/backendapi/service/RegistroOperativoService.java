package com.smartenergy.backendapi.service;

import com.smartenergy.backendapi.model.RegistroOperativo;
import com.smartenergy.backendapi.repository.RegistroOperativoRepository;
import org.springframework.stereotype.Service;

@Service
public class RegistroOperativoService extends BaseService<RegistroOperativo, RegistroOperativoRepository> {
    protected RegistroOperativoService(RegistroOperativoRepository repository) {
        super(repository);
    }
}
