package com.smartenergy.backendapi.service;

import com.smartenergy.backendapi.dto.DetalleEdificioDTO;
import com.smartenergy.backendapi.model.Aula;
import com.smartenergy.backendapi.model.Edificio;
import com.smartenergy.backendapi.repository.AulaRepository;
import com.smartenergy.backendapi.repository.EdificioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EdificioService extends BaseService<Edificio, EdificioRepository> {
    private final AulaRepository aulaRepository;
    private final CalculoService calculoService;

    protected EdificioService(EdificioRepository repository, AulaRepository aulaRepository, CalculoService calculoService) {
        super(repository);
        this.aulaRepository = aulaRepository;
        this.calculoService = calculoService;
    }

    public DetalleEdificioDTO findDetalle(UUID id) {
        DetalleEdificioDTO detalle = new DetalleEdificioDTO();
        List<Aula> aulas = aulaRepository.findByEdificioId(id).orElseThrow(() -> new RuntimeException("No existen aulas"));
        Edificio edificio = repo.findById(id).orElseThrow(() -> new RuntimeException("No existe edificio"));

        detalle.setAulas(aulas);
        detalle.setConsumo(calculoService.calcularConsumoEdificio(id));
        detalle.setNombre(edificio.getNombre());

        return detalle;
    }
}
