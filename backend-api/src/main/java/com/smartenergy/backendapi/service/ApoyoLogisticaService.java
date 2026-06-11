package com.smartenergy.backendapi.service;

import com.smartenergy.backendapi.model.ApoyoLogistica;
import com.smartenergy.backendapi.repository.IApoyoLogisticaRepository;
import org.springframework.stereotype.Service;

@Service
public class ApoyoLogisticaService extends BaseService<ApoyoLogistica, IApoyoLogisticaRepository>{
    public ApoyoLogisticaService(IApoyoLogisticaRepository repo) {
        super(repo);
    }
}
