package com.smartenergy.backendapi.controller;

import com.smartenergy.backendapi.model.AsignacionEdificio;
import com.smartenergy.backendapi.model.Incidencia;
import com.smartenergy.backendapi.service.AsignacionEdificioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/asignacion")
public class AsignacionEdificioController {
    private final AsignacionEdificioService service;

    public AsignacionEdificioController(AsignacionEdificioService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public ResponseEntity<List<AsignacionEdificio>> findAll() {return ResponseEntity.ok(service.findAll());}

    @GetMapping("/{id}")
    public ResponseEntity<AsignacionEdificio> findById(@PathVariable UUID id) {return ResponseEntity.ok(service.findById(id));}

    @PostMapping("/save")
    public ResponseEntity<AsignacionEdificio> save(@RequestBody AsignacionEdificio asignacionEdificio) {return ResponseEntity.ok(service.save(asignacionEdificio));}

    @PutMapping("/update")
    public ResponseEntity<AsignacionEdificio> update(@RequestBody AsignacionEdificio asignacionEdificio) {
        AsignacionEdificio i = service.findById(asignacionEdificio.getId());
        i.setActivo(asignacionEdificio.isActivo());
        i.setFechaAsignacion(asignacionEdificio.getFechaAsignacion());
        i.setEdificio(asignacionEdificio.getEdificio());
        i.setApoyoLogistica(asignacionEdificio.getApoyoLogistica());

        return ResponseEntity.ok(service.save(i));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
