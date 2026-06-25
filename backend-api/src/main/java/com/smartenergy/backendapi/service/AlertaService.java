package com.smartenergy.backendapi.service;

import com.smartenergy.backendapi.model.Alerta;
import com.smartenergy.backendapi.model.Aula;
import com.smartenergy.backendapi.model.Equipo;
import com.smartenergy.backendapi.model.Estado;
import com.smartenergy.backendapi.model.EstadoAlerta;
import com.smartenergy.backendapi.repository.IAlertaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class AlertaService {
    private final IAlertaRepository repo;
    private final RegistroOperativoService registroOperativoService;

    public AlertaService(IAlertaRepository repo, RegistroOperativoService registroOperativoService) {
        this.repo = repo;
        this.registroOperativoService = registroOperativoService;
    }

    public Alerta save(Alerta alerta) {
        if(alerta.getEstado() == EstadoAlerta.ATENDIDA) {
            atenderAlerta(alerta);
        }
        return repo.save(alerta);
    }

    public List<Alerta> findAll() {return repo.findAll();}

    public Alerta findById(UUID id) {return repo.findById(id).orElseThrow(() -> new RuntimeException("No se encontro la Alerta"));}

    public void delete(UUID id) { repo.deleteById(id); }

    public void generarAlerta(Aula aula, String tipo) {
        boolean existe = repo.existsByAulaAndTipoAlertaAndEstado(aula, tipo, EstadoAlerta.PENDIENTE);

        if (existe) { return; }

        Alerta alerta = new Alerta();

        alerta.setTipoAlerta(tipo);
        alerta.setAula(aula);
        alerta.setFechaHora(LocalDateTime.now());
        alerta.setEstado(EstadoAlerta.PENDIENTE);

        repo.save(alerta);
    }

    public void atenderAlerta(Alerta alerta) {
        Equipo equipo = alerta.getAula().getEquipo();

        switch(alerta.getTipoAlerta()) {
            case "Falta Climatizacion":
                registroOperativoService.cambiarEstado(equipo, Estado.ENCENDIDO);
                break;
            case "Desperdicio":
                registroOperativoService.cambiarEstado(equipo, Estado.APAGADO);
                break;
        }

        alerta.setEstado(EstadoAlerta.ATENDIDA);
    }
}
