package com.smartenergy.backendapi.service;

import com.smartenergy.backendapi.model.ConfigSistema;
import com.smartenergy.backendapi.repository.IConfigSistemaRepository;
import org.springframework.stereotype.Service;

@Service
public class ConfigSistemaService extends BaseService<ConfigSistema, IConfigSistemaRepository>{
    public ConfigSistemaService(IConfigSistemaRepository repo) {
        super(repo);
    }
}
