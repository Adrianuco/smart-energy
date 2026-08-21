package com.smartenergy.backendapi.service;

import com.smartenergy.backendapi.model.Aula;
import com.smartenergy.backendapi.model.Equipo;
import com.smartenergy.backendapi.model.Estado;
import com.smartenergy.backendapi.repository.EquipoRepository;
import com.smartenergy.backendapi.repository.RegistroOperativoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipoService extends BaseService<Equipo, EquipoRepository> {

    private final RegistroOperativoService registroOperativoService;

    protected EquipoService(EquipoRepository repository, RegistroOperativoService registroOperativoService) {
        super(repository);
        this.registroOperativoService = registroOperativoService;
    }

    // metodo para crear un equipo a un aula segun un modelo
    public Equipo crearEquipoParaAula(Aula aula, Equipo modelo) {

        Equipo equipo = new Equipo();

        equipo.setMarca(modelo.getMarca());
        equipo.setModelo(modelo.getModelo());
        equipo.setBtu(modelo.getBtu());
        equipo.setEficiencia(modelo.getEficiencia());
        equipo.setPotenciaMinima(modelo.getPotenciaMinima());
        equipo.setPotenciaNominal(modelo.getPotenciaNominal());

        equipo.setOperativo(true);
        equipo.setEstado(Estado.APAGADO);
        equipo.setAula(aula);

        Equipo guardado = repo.save(equipo);

        registroOperativoService.cambiarEstado(guardado, Estado.APAGADO);

        return guardado;
    }

    public void asignarEquipos(List<Aula> aulas, Equipo modelo) {
        for (Aula aula: aulas) {
            crearEquipoParaAula(aula, modelo);
        }
    }

}
