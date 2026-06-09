package com.smartenergy.backendapi.controller;

import com.smartenergy.backendapi.model.Aula;
import com.smartenergy.backendapi.service.AulaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/aulas")
public class AulaController {
    private final AulaService service;

    public AulaController(AulaService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Aula>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping("/save")
    public ResponseEntity<Aula> save(@RequestBody Aula aula) {
        return ResponseEntity.ok(service.save(aula));
    }
}
