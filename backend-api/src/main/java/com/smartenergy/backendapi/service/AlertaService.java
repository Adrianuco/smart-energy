package com.smartenergy.backendapi.service;

import com.smartenergy.backendapi.model.Alerta;
import com.smartenergy.backendapi.repository.IAlertaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AlertaService {
    private final IAlertaRepository repo;

    public AlertaService(IAlertaRepository repo) {
        this.repo = repo;
    }

    public Alerta save(Alerta alerta) {return repo.save(alerta);}

    public List<Alerta> findAll() {return repo.findAll();}

    public Alerta findById(UUID id) {return repo.findById(id).orElseThrow(() -> new RuntimeException("No se encontro la Alerta"));}

    public void delete(UUID id) { repo.deleteById(id); }
}
