package com.smartenergy.backendapi.service;

import com.smartenergy.backendapi.model.Aula;
import com.smartenergy.backendapi.model.HorarioAcademico;
import com.smartenergy.backendapi.repository.HorarioAcademicoRepository;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
public class HorarioAcademicoService extends BaseService<HorarioAcademico, HorarioAcademicoRepository> {
    private final AulaService aulaService;

    protected HorarioAcademicoService(HorarioAcademicoRepository repository, AulaService aulaService) {
        super(repository);
        this.aulaService = aulaService;
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
                String codigo = row.getCell(0).getStringCellValue();
                int dia = (int) row.getCell(1).getNumericCellValue();
                LocalTime horaInicio = row.getCell(2).getLocalDateTimeCellValue().toLocalTime();
                LocalTime horaFin = row.getCell(3).getLocalDateTimeCellValue().toLocalTime();
                String asignatura = row.getCell(4).getStringCellValue();

                Aula aula = aulaService.findByCodigo(codigo);
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
