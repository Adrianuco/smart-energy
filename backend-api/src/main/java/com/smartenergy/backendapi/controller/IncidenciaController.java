package com.smartenergy.backendapi.controller;

import com.smartenergy.backendapi.model.Alerta;
import com.smartenergy.backendapi.model.Incidencia;
import com.smartenergy.backendapi.service.IncidenciaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/incidencias")
public class IncidenciaController {
    private final IncidenciaService service;

    public IncidenciaController(IncidenciaService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Incidencia>> findAll() {return ResponseEntity.ok(service.findAll());}

    @GetMapping("/{id}")
    public ResponseEntity<Incidencia> findById(@PathVariable UUID id) {return ResponseEntity.ok(service.findById(id));}

    @PostMapping("/save")
    public ResponseEntity<Incidencia> save(@RequestBody Incidencia incidencia) {return ResponseEntity.ok(service.save(incidencia));}

    @PutMapping("/update")
    public ResponseEntity<Incidencia> update(@RequestBody Incidencia incidencia) {
        Incidencia i = service.findById(incidencia.getId());
        i.setDescripcion(incidencia.getDescripcion());
        i.setAula(incidencia.getAula());
        i.setFechaHora(incidencia.getFechaHora());
        i.setTipoIncidencia(incidencia.getTipoIncidencia());

        return ResponseEntity.ok(service.save(i));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
