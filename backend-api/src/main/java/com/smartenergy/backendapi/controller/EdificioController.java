package com.smartenergy.backendapi.controller;

import com.smartenergy.backendapi.dto.EdificioDTO;
import com.smartenergy.backendapi.model.Edificio;
import com.smartenergy.backendapi.service.EdificioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/edificio")
public class EdificioController {
    private final EdificioService service;

    public EdificioController(EdificioService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public ResponseEntity<List<EdificioDTO>> findAll() {return ResponseEntity.ok(service.findAllDTO());}

    @GetMapping("/{id}")
    public ResponseEntity<EdificioDTO> findById(@PathVariable UUID id) {return ResponseEntity.ok(service.findDTOById(id));}

    @PostMapping("/save")
    public ResponseEntity<Edificio> save(@RequestBody Edificio edificio) {return ResponseEntity.ok(service.save(edificio));}

    @PutMapping("/update")
    public ResponseEntity<Edificio> update(@RequestBody Edificio edificio) {
        Edificio i = service.findById(edificio.getId());
        i.setNombre(edificio.getNombre());

        return ResponseEntity.ok(service.save(i));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/detalle/{id}")
    public ResponseEntity<EdificioDTO> findDetalle(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findDetalle(id));
    }
}
