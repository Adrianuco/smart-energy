package com.smartenergy.backendapi.controller;

import com.smartenergy.backendapi.model.Administrador;
import com.smartenergy.backendapi.model.ApoyoLogistica;
import com.smartenergy.backendapi.service.ApoyoLogisticaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/logistica")
public class ApoyoLogisticaController {
    private final ApoyoLogisticaService service;

    public ApoyoLogisticaController(ApoyoLogisticaService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ApoyoLogistica>> findAll() {return ResponseEntity.ok(service.findAll());}

    @GetMapping("/{id}")
    public ResponseEntity<ApoyoLogistica> findById(@PathVariable UUID id) {return ResponseEntity.ok(service.findById(id));}

    @PostMapping("/save")
    public ResponseEntity<ApoyoLogistica> save(@RequestBody ApoyoLogistica apoyoLogistica) {return ResponseEntity.ok(service.save(apoyoLogistica));}

    @PutMapping("/update")
    public ResponseEntity<ApoyoLogistica> update(@RequestBody ApoyoLogistica apoyoLogistica) {
        ApoyoLogistica i = service.findById(apoyoLogistica.getId());
        i.setNombre(apoyoLogistica.getNombre());
        i.setApellido(apoyoLogistica.getApellido());
        i.setCif(apoyoLogistica.getCif());
        i.setPassword(apoyoLogistica.getPassword());
        i.setActivo(apoyoLogistica.isActivo());

        return ResponseEntity.ok(service.save(i));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
