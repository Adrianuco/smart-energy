package com.smartenergy.backendapi.service;

import com.smartenergy.backendapi.model.AsignacionEdificio;
import com.smartenergy.backendapi.repository.IAsignacionEdificioRepository;
import org.springframework.stereotype.Service;

@Service
public class AsignacionEdificioService extends BaseService<AsignacionEdificio, IAsignacionEdificioRepository>{
    public AsignacionEdificioService(IAsignacionEdificioRepository repo) {
        super(repo);
    }


}
