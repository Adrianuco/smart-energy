package com.smartenergy.backendapi.service;

import com.smartenergy.backendapi.model.Administrador;
import com.smartenergy.backendapi.repository.IAdministradorRepository;
import org.springframework.stereotype.Service;

@Service
public class AdministradorService extends BaseService<Administrador, IAdministradorRepository>{
    public AdministradorService(IAdministradorRepository repo) {
        super(repo);
    }
}
