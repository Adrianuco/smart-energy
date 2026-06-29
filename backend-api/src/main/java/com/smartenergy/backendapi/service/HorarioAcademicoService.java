package com.smartenergy.backendapi.service;

import com.smartenergy.backendapi.model.Aula;
import com.smartenergy.backendapi.model.Edificio;
import com.smartenergy.backendapi.model.HorarioAcademico;
import com.smartenergy.backendapi.model.Equipo;
import com.smartenergy.backendapi.model.Estado;
import com.smartenergy.backendapi.repository.AulaRepository;
import com.smartenergy.backendapi.repository.EdificioRepository;
import com.smartenergy.backendapi.repository.HorarioAcademicoRepository;
import com.smartenergy.backendapi.repository.EquipoRepository;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class HorarioAcademicoService extends BaseService<HorarioAcademico, HorarioAcademicoRepository> {
    private final AulaRepository aulaRepository;
    private final EdificioRepository edificioRepository;

    protected HorarioAcademicoService(
            HorarioAcademicoRepository repository,
            AulaRepository aulaRepository,
            EdificioRepository edificioRepository
    ) {
        super(repository);
        this.aulaRepository = aulaRepository;
        this.edificioRepository = edificioRepository;
    }

    // metodo para importar un excel
    public void importar(MultipartFile file) {
        // hacemos un try-with-resources
        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
            // seleccionamos la hoja del excel e inicializamos la lista de horarios
            Sheet sheet = workbook.getSheetAt(0);
            List<HorarioAcademico> horarios = new ArrayList<>();

            boolean primeraFila = true;
            // recorremos el excel por filas
            for (Row row : sheet) {

                // nos saltamos la primera fila de encabezados
                if (primeraFila) {
                    primeraFila = false;
                    continue;
                }

                // recolectamos los datos de la fila actual
                String edificioNombre = row.getCell(0).getStringCellValue();
                String aulaCodigo = row.getCell(1).getStringCellValue();
                int dia = (int) row.getCell(2).getNumericCellValue();
                LocalTime horaInicio = row.getCell(3).getLocalDateTimeCellValue().toLocalTime();
                LocalTime horaFin = row.getCell(4).getLocalDateTimeCellValue().toLocalTime();
                String asignatura = row.getCell(5).getStringCellValue();

                // buscamos el edificio por el nombre, si ya existe lo guardamos en edificio, si no existe, lo creamos
                Edificio edificio = edificioRepository.findByNombre(edificioNombre)
                    .orElseGet(() -> {
                        Edificio e = new Edificio();
                        e.setNombre(edificioNombre);
                        return edificioRepository.save(e);
                    });

                // buscamos el aula por su codigo y hacemos lo mismo que con el edificio
                Aula aula = aulaRepository.findByCodigo(aulaCodigo)
                    .orElseGet(() -> {
                        Aula a = new Aula();
                        a.setCodigo(aulaCodigo);
                        a.setEdificio(edificio);
                        return aulaRepository.save(a);
                    });

                // verificamos si ese horario en especifico ya existe
                boolean duplicate = repo.existsByAulaAndDiaSemanaAndHoraInicioAndHoraFin(aula, dia, horaInicio, horaFin);
                if (duplicate) {
                    continue;
                }

                // instanciamos el horario academico y lo agregamos a la lista
                HorarioAcademico horarioAcademico = new HorarioAcademico();
                horarioAcademico.setAula(aula);
                horarioAcademico.setDiaSemana(dia);
                horarioAcademico.setAsignatura(asignatura);
                horarioAcademico.setHoraFin(horaFin);
                horarioAcademico.setHoraInicio(horaInicio);

                horarios.add(horarioAcademico);
            }

            // guardamos todos los horarios
            repo.saveAll(horarios);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
