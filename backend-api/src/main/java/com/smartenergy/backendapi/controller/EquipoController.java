package com.smartenergy.backendapi.controller;

import com.smartenergy.backendapi.model.Equipo;
import com.smartenergy.backendapi.service.EquipoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/equipo")
public class EquipoController {
    private final EquipoService service;

    public EquipoController(EquipoService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Equipo>> findAll() {return ResponseEntity.ok(service.findAll());}

    @GetMapping("/{id}")
    public ResponseEntity<Equipo> findById(@PathVariable UUID id) {return ResponseEntity.ok(service.findById(id));}

    @PostMapping("/save")
    public ResponseEntity<Equipo> save(@RequestBody Equipo equipo) {return ResponseEntity.ok(service.save(equipo));}

    @PutMapping("/update")
    public ResponseEntity<Equipo> update(@RequestBody Equipo equipo) {
        Equipo i = service.findById(equipo.getId());
        i.setMarca(equipo.getMarca());
        i.setModelo(equipo.getModelo());
        i.setBtu(equipo.getBtu());
        i.setEficiencia(equipo.getEficiencia());
        i.setOperativo(equipo.getOperativo());
        i.setPotenciaMinima(equipo.getPotenciaMinima());
        i.setPotenciaNominal(equipo.getPotenciaNominal());
        i.setAula(equipo.getAula());

        return ResponseEntity.ok(service.save(i));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
