package com.smartenergy.backendapi.controller;

import com.smartenergy.backendapi.model.Administrador;
import com.smartenergy.backendapi.model.Incidencia;
import com.smartenergy.backendapi.service.AdministradorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/adminstradores")
public class AdministradorController {

    private final AdministradorService service;

    public AdministradorController(AdministradorService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Administrador>> findAll() {return ResponseEntity.ok(service.findAll());}

    @GetMapping("/{id}")
    public ResponseEntity<Administrador> findById(@PathVariable UUID id) {return ResponseEntity.ok(service.findById(id));}

    @PostMapping("/save")
    public ResponseEntity<Administrador> save(@RequestBody Administrador administrador) {return ResponseEntity.ok(service.save(administrador));}

    @PutMapping("/update")
    public ResponseEntity<Administrador> update(@RequestBody Administrador administrador) {
        Administrador i = service.findById(administrador.getId());
        i.setNombre(administrador.getNombre());
        i.setApellido(administrador.getApellido());
        i.setNivelAcceso(administrador.getNivelAcceso());
        i.setCif(administrador.getCif());
        i.setPassword(administrador.getPassword());
        i.setActivo(administrador.isActivo());

        return ResponseEntity.ok(service.save(i));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
