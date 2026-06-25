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
    private final EquipoRepository equipoRepository;

    protected HorarioAcademicoService(
            HorarioAcademicoRepository repository,
            AulaRepository aulaRepository,
            EdificioRepository edificioRepository,
            EquipoRepository equipoRepository
    ) {
        super(repository);
        this.aulaRepository = aulaRepository;
        this.edificioRepository = edificioRepository;
        this.equipoRepository = equipoRepository;
    }

    public void importar(MultipartFile file) {
        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            List<HorarioAcademico> horarios = new ArrayList<>();

            boolean primeraFila = true;
            for (Row row : sheet) {
                if (primeraFila) {
                    primeraFila = false;
                    continue;
                }

                if (row == null || row.getCell(0) == null) {
                    continue;
                }

                String edificioNombre = row.getCell(0).getStringCellValue();
                String aulaCodigo = row.getCell(1).getStringCellValue();
                int dia = (int) row.getCell(2).getNumericCellValue();
                LocalTime horaInicio = row.getCell(3).getLocalDateTimeCellValue().toLocalTime();
                LocalTime horaFin = row.getCell(4).getLocalDateTimeCellValue().toLocalTime();
                String asignatura = row.getCell(5).getStringCellValue();

                Edificio edificio = edificioRepository.findByNombre(edificioNombre)
                    .orElseGet(() -> {
                        Edificio e = new Edificio();
                        e.setNombre(edificioNombre);
                        return edificioRepository.save(e);
                    });

                Aula aula = aulaRepository.findByCodigo(aulaCodigo)
                    .orElseGet(() -> {
                        Aula a = new Aula();
                        a.setCodigo(aulaCodigo);
                        a.setEdificio(edificio);
                        return aulaRepository.save(a);
                    });

                boolean duplicate = repo.existsByAulaAndDiaSemanaAndHoraInicioAndHoraFin(aula, dia, horaInicio, horaFin);
                if (duplicate) {
                    continue;
                }

                HorarioAcademico horarioAcademico = new HorarioAcademico();
                horarioAcademico.setAula(aula);
                horarioAcademico.setDiaSemana(dia);
                horarioAcademico.setAsignatura(asignatura);
                horarioAcademico.setHoraFin(horaFin);
                horarioAcademico.setHoraInicio(horaInicio);

                horarios.add(horarioAcademico);
            }

            repo.saveAll(horarios);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
