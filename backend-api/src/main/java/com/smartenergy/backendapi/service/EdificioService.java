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

    // buscar todos los edificios
    public List<EdificioDTO> findAllDTO() {

        // findAll pero se convierten a DTO para android studio
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

    // metodo para convertir un edificio en un dto con todos sus valores calculados
    private EdificioDTO convertToDTO(Edificio edificio) {

        EdificioDTO dto = new EdificioDTO();

        // se le agregan los datos del edificio al dto
        dto.setId(edificio.getId());
        dto.setNombre(edificio.getNombre());


        // se calculan los consumos y ahorros de ese edifico en especifico
        dto.setConsumo(calculoService.calcularConsumoEdificio(edificio.getId()));
        dto.setConsumoEsperado(calculoService.calcularConsumoEsperadoEdificio(edificio.getId()));
        dto.setAhorro(calculoService.calcularAhorroEdificio(edificio.getId()));

        // se verifica si tiene alertas activas
        boolean tieneAlertas =
                alertaRepository.existsByAulaEdificioIdAndEstado(
                        edificio.getId(),
                        EstadoAlerta.PENDIENTE
                );

        // se define el estado del edificio en base a si tiene alertas o no
        dto.setEstado(
                tieneAlertas ? "Problemas" : "OK"
        );


        return dto;
    }

    // dto especifico para el detalle edificio en la app
    public EdificioDTO findDetalle(UUID id) {

        // se busca el edificio
        Edificio edificio = repo.findById(id).orElseThrow(() -> new RuntimeException("No existe edificio"));


        EdificioDTO detalle = new EdificioDTO();

        // se establecen los datos
        detalle.setNombre(edificio.getNombre());
        detalle.setAulas(aulaRepository.findByEdificioId(id));
        detalle.setConsumo(calculoService.calcularConsumoEdificio(id));
        detalle.setAhorro(calculoService.calcularAhorroEdificio(id));


        return detalle;
    }
}