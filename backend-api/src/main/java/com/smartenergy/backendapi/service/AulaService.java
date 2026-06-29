package com.smartenergy.backendapi.service;

import com.smartenergy.backendapi.model.Aula;
import com.smartenergy.backendapi.model.Equipo;
import com.smartenergy.backendapi.model.Estado;
import com.smartenergy.backendapi.repository.AulaRepository;
import com.smartenergy.backendapi.repository.EquipoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class AulaService extends BaseService<Aula, AulaRepository> {

    private final EquipoRepository equipoRepository;
    private final RegistroOperativoService registroOperativoService;

    protected AulaService(
            AulaRepository repository,
            EquipoRepository equipoRepository,
            RegistroOperativoService registroOperativoService
    ) {
        super(repository);
        this.equipoRepository = equipoRepository;
        this.registroOperativoService = registroOperativoService;
    }

    @Override
    @Transactional
    // metodo utilizado en la creacion de aulas manuales
    public Aula save(Aula aula) {
        // verificar si el aula ya existe
        if (aula.getId() != null) {
            Aula existing = repo.findById(aula.getId()).orElse(null);
            // verificamos si realmente existe el aula y su equipo
            if (existing != null && existing.getEquipo() != null) {
                return repo.save(aula);
            }
        }

        // aula nueva o no tiene equipo

        // se guarda una copia del equipo que trae el aula
        Equipo equipoModel = aula.getEquipo();

        // desasociamos el equipo del aula
        aula.setEquipo(null);
        Aula savedAula = repo.save(aula);

        // en caso de que el aula si tenia equipo
        if (equipoModel != null) {
            // creamos un nuevo equipo basandonos en la copia que guardamos antes
            Equipo nuevoEquipo = new Equipo();
            nuevoEquipo.setMarca(equipoModel.getMarca());
            nuevoEquipo.setModelo(equipoModel.getModelo());
            nuevoEquipo.setBtu(equipoModel.getBtu());
            nuevoEquipo.setEficiencia(equipoModel.getEficiencia());
            nuevoEquipo.setOperativo(true);
            nuevoEquipo.setPotenciaMinima(equipoModel.getPotenciaMinima());
            nuevoEquipo.setPotenciaNominal(equipoModel.getPotenciaNominal());
            // se inicializa como apagado y el nuevo se asocia al aula creada
            nuevoEquipo.setEstado(Estado.APAGADO);
            nuevoEquipo.setAula(savedAula);
            nuevoEquipo = equipoRepository.save(nuevoEquipo);

            // se inicializa el registro operativo del equipo
            registroOperativoService.cambiarEstado(nuevoEquipo, Estado.APAGADO);

            savedAula.setEquipo(nuevoEquipo);
        }

        return savedAula;
    }

    public List<Aula> findByEdificioId(UUID edificioId) {
        return repo.findByEdificioId(edificioId);
    }
}
