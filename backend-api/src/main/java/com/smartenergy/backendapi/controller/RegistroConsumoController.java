package com.smartenergy.backendapi.controller;

import com.smartenergy.backendapi.model.RegistroConsumo;
import com.smartenergy.backendapi.service.RegistroConsumoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/registro")
public class RegistroConsumoController {
    private final RegistroConsumoService service;

    public RegistroConsumoController(RegistroConsumoService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public ResponseEntity<List<RegistroConsumo>> findAll() {return ResponseEntity.ok(service.findAll());}

    @GetMapping("/{id}")
    public ResponseEntity<RegistroConsumo> findById(@PathVariable UUID id) {return ResponseEntity.ok(service.findById(id));}

    @PostMapping("/save")
    public ResponseEntity<RegistroConsumo> save(@RequestBody RegistroConsumo registroConsumo) {return ResponseEntity.ok(service.save(registroConsumo));}

    @PutMapping("/update")
    public ResponseEntity<RegistroConsumo> update(@RequestBody RegistroConsumo registroConsumo) {
        RegistroConsumo i = service.findById(registroConsumo.getId());
        i.setConsumo(registroConsumo.getConsumo());
        i.setFecha(registroConsumo.getFecha());
        i.setHoraFin(registroConsumo.getHoraFin());
        i.setHoraInicio(registroConsumo.getHoraInicio());
        i.setEquipo(registroConsumo.getEquipo());

        return ResponseEntity.ok(service.save(i));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
