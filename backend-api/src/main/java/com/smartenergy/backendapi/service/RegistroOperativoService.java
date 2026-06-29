package com.smartenergy.backendapi.service;

import com.smartenergy.backendapi.model.Equipo;
import com.smartenergy.backendapi.model.Estado;
import com.smartenergy.backendapi.model.RegistroOperativo;
import com.smartenergy.backendapi.repository.RegistroOperativoRepository;
import com.smartenergy.backendapi.repository.EquipoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class RegistroOperativoService extends BaseService<RegistroOperativo, RegistroOperativoRepository> {
    private final CalculoService calculoService;
    private final EquipoRepository equipoRepository;

    protected RegistroOperativoService(
        RegistroOperativoRepository repository,
        CalculoService calculoService,
        EquipoRepository equipoRepository
    ) {
        super(repository);
        this.calculoService = calculoService;
        this.equipoRepository = equipoRepository;
    }

    // metodo para cambiar el estado de un reguistro operativo
    public void cambiarEstado(Equipo equipo, Estado nuevoEstado) {
        // buscamos si existe el equipo
        Equipo equipoManaged = equipoRepository.findById(equipo.getId())
                .orElseThrow(() -> new RuntimeException("No se encontró el equipo"));

        // establecemos el nuevo estado del equipo y guardamos
        equipoManaged.setEstado(nuevoEstado);
        equipoRepository.save(equipoManaged);

        // buscamos el registro operativo actual
        Optional<RegistroOperativo> actual = repo.findByEquipoAndFinIsNull(equipoManaged);

        // cerramos el registro operativo actual y calculamos su consumo hasta ese momento
        if (actual.isPresent()) {
            RegistroOperativo registroActual = actual.get();
            registroActual.setFin(LocalDateTime.now());
            calculoService.calcularConsumoRegistro(registroActual);
            repo.save(registroActual);
        }

        // inicializamos un nuevo registro operativo para el equipo con el tiempo actual
        RegistroOperativo nuevoRegistro = new RegistroOperativo();

        nuevoRegistro.setEquipo(equipoManaged);
        nuevoRegistro.setEstado(nuevoEstado);
        nuevoRegistro.setInicio(LocalDateTime.now());
        nuevoRegistro.setConsumo(0);

        repo.save(nuevoRegistro);
    }
}
