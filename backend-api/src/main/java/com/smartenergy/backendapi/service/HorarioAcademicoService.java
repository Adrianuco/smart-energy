package com.smartenergy.backendapi.service;

import com.smartenergy.backendapi.model.HorarioAcademico;
import com.smartenergy.backendapi.repository.HorarioAcademicoRepository;
import org.springframework.stereotype.Service;

@Service
public class HorarioAcademicoService extends BaseService<HorarioAcademico, HorarioAcademicoRepository> {
    protected HorarioAcademicoService(HorarioAcademicoRepository repository) {
        super(repository);
    }
}
