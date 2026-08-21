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

    // metodo para generar una alerta
    public void generarAlerta(Aula aula, String tipo) {
        // se verifica si la alerta ya existe
        boolean existe = repo.existsByAulaAndTipoAlertaAndEstado(aula, tipo, EstadoAlerta.PENDIENTE);
        if (existe) { return; }

        // si no existe se instancia la alerta nueva
        Alerta alerta = new Alerta();

        alerta.setTipoAlerta(tipo);
        alerta.setAula(aula);
        alerta.setFechaHora(LocalDateTime.now());
        alerta.setEstado(EstadoAlerta.PENDIENTE);

        repo.save(alerta);
    }

    // metodo que representa la accion fisica de atender una alerta
    public void atenderAlerta(Alerta alerta) {
        // se obtiene el equipo asociado al aula de la alerta
        Equipo equipo = alerta.getAula().getEquipo();

        // switch para atender la alerta segun su tipo
        switch(alerta.getTipoAlerta()) {
            // al atender:
            case "Falta Climatización":
                // se cambia el estado del equipo a Encendido
                registroOperativoService.cambiarEstado(equipo, Estado.ENCENDIDO);
                break;
            case "Desperdicio":
                // se cambia el estado del equipo a Apagado
                registroOperativoService.cambiarEstado(equipo, Estado.APAGADO);
                break;
        }

        // una vez cambiado el estado, se marca como atendida
        alerta.setEstado(EstadoAlerta.ATENDIDA);
    }
}
