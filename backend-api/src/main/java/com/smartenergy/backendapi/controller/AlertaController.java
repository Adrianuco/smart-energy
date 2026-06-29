package com.smartenergy.backendapi.controller;

import com.smartenergy.backendapi.model.Alerta;
import com.smartenergy.backendapi.service.AlertaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/alertas")
public class AlertaController {
    private final AlertaService service;

    public AlertaController(AlertaService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Alerta>> findAll() {return ResponseEntity.ok(service.findAll());}

    @GetMapping("/{id}")
    public ResponseEntity<Alerta> findById(@PathVariable UUID id) {return ResponseEntity.ok(service.findById(id));}

    @PostMapping("/save")
    public ResponseEntity<Alerta> save(@RequestBody Alerta alerta) {return ResponseEntity.ok(service.save(alerta));}

    @PutMapping("/update")
    public ResponseEntity<Alerta> update(@RequestBody Alerta alerta) {
        Alerta a = service.findById(alerta.getId());
        a.setEstado(alerta.getEstado());

        return ResponseEntity.ok(service.save(a));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
