package com.smartenergy.backendapi.controller;

import com.smartenergy.backendapi.model.ConfigSistema;
import com.smartenergy.backendapi.model.Incidencia;
import com.smartenergy.backendapi.service.ConfigSistemaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/configuraciones")
public class ConfigSistemaController {
    private final ConfigSistemaService service;

    public ConfigSistemaController(ConfigSistemaService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ConfigSistema>> findAll() {return ResponseEntity.ok(service.findAll());}

    @GetMapping("/{id}")
    public ResponseEntity<ConfigSistema> findById(@PathVariable UUID id) {return ResponseEntity.ok(service.findById(id));}

    @PostMapping("/save")
    public ResponseEntity<ConfigSistema> save(@RequestBody ConfigSistema configSistema) {return ResponseEntity.ok(service.save(configSistema));}

    @PutMapping("/update")
    public ResponseEntity<ConfigSistema> update(@RequestBody ConfigSistema configSistema) {
        ConfigSistema i = service.findById(configSistema.getId());
        i.setMargenEncendido(configSistema.getMargenEncendido());
        i.setTiempoMinimoDesperdicio(configSistema.getTiempoMinimoDesperdicio());
        i.setActivo(configSistema.isActivo());

        return ResponseEntity.ok(service.save(i));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
