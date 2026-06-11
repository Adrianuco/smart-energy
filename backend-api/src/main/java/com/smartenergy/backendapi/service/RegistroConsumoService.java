package com.smartenergy.backendapi.service;

import com.smartenergy.backendapi.model.RegistroConsumo;
import com.smartenergy.backendapi.repository.RegistroConsumoRepository;
import org.springframework.stereotype.Service;

@Service
public class RegistroConsumoService extends BaseService<RegistroConsumo, RegistroConsumoRepository> {
    protected RegistroConsumoService(RegistroConsumoRepository repository) {
        super(repository);
    }
}
