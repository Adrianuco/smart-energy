package com.smartenergy.backendapi.service;

import com.smartenergy.backendapi.dto.EdificioDTO;
import com.smartenergy.backendapi.model.Edificio;
import com.smartenergy.backendapi.model.EstadoAlerta;
import com.smartenergy.backendapi.repository.AulaRepository;
import com.smartenergy.backendapi.repository.EdificioRepository;
import com.smartenergy.backendapi.repository.IAlertaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EdificioService extends BaseService<Edificio, EdificioRepository> {

    private final CalculoService calculoService;
    private final IAlertaRepository alertaRepository;
    private final AulaRepository aulaRepository;

    protected EdificioService(
            EdificioRepository repository,
            CalculoService calculoService,
            IAlertaRepository alertaRepository,
            AulaRepository aulaRepository
    ) {
        super(repository);
        this.calculoService = calculoService;
        this.alertaRepository = alertaRepository;
        this.aulaRepository = aulaRepository;
    }

    
    public List<EdificioDTO> findAllDTO() {

        return repo.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }


    public EdificioDTO findDTOById(UUID id) {

        Edificio edificio = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe edificio"));

        return convertToDTO(edificio);
    }


    private EdificioDTO convertToDTO(Edificio edificio) {

        EdificioDTO dto = new EdificioDTO();

        dto.setId(edificio.getId());
        dto.setNombre(edificio.getNombre());


        dto.setConsumo(
                calculoService.calcularConsumoEdificio(edificio.getId())
        );

        dto.setConsumoEsperado(
                calculoService.calcularConsumoEsperadoEdificio(edificio.getId())
        );

        dto.setAhorro(
                calculoService.calcularAhorroEdificio(edificio.getId())
        );


        boolean tieneAlertas =
                alertaRepository.existsByAulaEdificioIdAndEstado(
                        edificio.getId(),
                        EstadoAlerta.PENDIENTE
                );


        dto.setEstado(
                tieneAlertas ? "Problemas" : "OK"
        );


        dto.setCantidadAulas(
                aulaRepository.countByEdificioId(edificio.getId())
        );


        return dto;
    }


    public EdificioDTO findDetalle(UUID id) {

        Edificio edificio = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe edificio"));


        EdificioDTO detalle = new EdificioDTO();

        detalle.setNombre(edificio.getNombre());

        detalle.setAulas(
                aulaRepository.findByEdificioId(id)
        );


        detalle.setConsumo(
                calculoService.calcularConsumoEdificio(id)
        );


        detalle.setAhorro(
                calculoService.calcularAhorroEdificio(id)
        );


        return detalle;
    }
}