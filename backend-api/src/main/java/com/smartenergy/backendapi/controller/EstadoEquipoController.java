package com.smartenergy.backendapi.controller;

import com.smartenergy.backendapi.model.EstadoEquipo;
import com.smartenergy.backendapi.service.EstadoEquipoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/estado")
public class EstadoEquipoController {
    private final EstadoEquipoService service;

    public EstadoEquipoController(EstadoEquipoService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public ResponseEntity<List<EstadoEquipo>> findAll() {return ResponseEntity.ok(service.findAll());}

    @GetMapping("/{id}")
    public ResponseEntity<EstadoEquipo> findById(@PathVariable UUID id) {return ResponseEntity.ok(service.findById(id));}

    @PostMapping("/save")
    public ResponseEntity<EstadoEquipo> save(@RequestBody EstadoEquipo estadoEquipo) {return ResponseEntity.ok(service.save(estadoEquipo));}

    @PutMapping("/update")
    public ResponseEntity<EstadoEquipo> update(@RequestBody EstadoEquipo estadoEquipo) {
        EstadoEquipo i = service.findById(estadoEquipo.getId());
        i.setEstado(estadoEquipo.getEstado());
        i.setFecha(estadoEquipo.getFecha());
        i.setHoraFin(estadoEquipo.getHoraFin());
        i.setHoraInicio(estadoEquipo.getHoraInicio());

        return ResponseEntity.ok(service.save(i));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
