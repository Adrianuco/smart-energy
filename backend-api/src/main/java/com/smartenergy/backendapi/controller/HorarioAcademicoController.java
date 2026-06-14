package com.smartenergy.backendapi.controller;

import com.smartenergy.backendapi.model.HorarioAcademico;
import com.smartenergy.backendapi.service.HorarioAcademicoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/horario")
public class HorarioAcademicoController {
    private final HorarioAcademicoService service;

    public HorarioAcademicoController(HorarioAcademicoService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public ResponseEntity<List<HorarioAcademico>> findAll() {return ResponseEntity.ok(service.findAll());}

    @GetMapping("/{id}")
    public ResponseEntity<HorarioAcademico> findById(@PathVariable UUID id) {return ResponseEntity.ok(service.findById(id));}

    @PostMapping("/save")
    public ResponseEntity<HorarioAcademico> save(@RequestBody HorarioAcademico horarioAcademico) {return ResponseEntity.ok(service.save(horarioAcademico));}

    @PutMapping("/update")
    public ResponseEntity<HorarioAcademico> update(@RequestBody HorarioAcademico horarioAcademico) {
        HorarioAcademico i = service.findById(horarioAcademico.getId());
        i.setAsignatura(horarioAcademico.getAsignatura());
        i.setDiaSemana(horarioAcademico.getDiaSemana());
        i.setHoraFin(horarioAcademico.getHoraFin());
        i.setHoraInicio(horarioAcademico.getHoraInicio());
        i.setAula(horarioAcademico.getAula());

        return ResponseEntity.ok(service.save(i));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
        service.importar(file);
        return ResponseEntity.ok("Horarios Importados");
    }
}
