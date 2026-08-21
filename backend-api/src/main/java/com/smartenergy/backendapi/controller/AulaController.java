package com.smartenergy.backendapi.controller;

import com.smartenergy.backendapi.model.Aula;
import com.smartenergy.backendapi.service.AulaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/aula")
public class AulaController {

    private final AulaService service;

    public AulaController(AulaService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Aula>> findAll() {return ResponseEntity.ok(service.findAll());}

    @GetMapping("/{id}")
    public ResponseEntity<Aula> findById(@PathVariable UUID id) {return ResponseEntity.ok(service.findById(id));}

    @PostMapping("/save")
    public ResponseEntity<Aula> save(@RequestBody Aula aula) {return ResponseEntity.ok(service.save(aula));}

    @PutMapping("/update")
    public ResponseEntity<Aula> update(@RequestBody Aula aula) {
        Aula i = service.findById(aula.getId());
        i.setEdificio(aula.getEdificio());
        i.setCodigo(aula.getCodigo());


        return ResponseEntity.ok(service.save(i));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/edificio/{id}")
    public ResponseEntity<List<Aula>> findByEdificioId(@PathVariable UUID id) {return ResponseEntity.ok(service.findByEdificioId(id));}
}
