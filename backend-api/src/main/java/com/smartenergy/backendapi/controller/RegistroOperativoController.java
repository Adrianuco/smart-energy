package com.smartenergy.backendapi.controller;

import com.smartenergy.backendapi.model.Equipo;
import com.smartenergy.backendapi.model.Estado;
import com.smartenergy.backendapi.model.RegistroOperativo;
import com.smartenergy.backendapi.service.EquipoService;
import com.smartenergy.backendapi.service.RegistroOperativoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/registro")
public class RegistroOperativoController {
    private final RegistroOperativoService service;
    private final EquipoService equipoService;

    public RegistroOperativoController(RegistroOperativoService service, EquipoService equipoService) {
        this.service = service;
        this.equipoService = equipoService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<RegistroOperativo>> findAll() {return ResponseEntity.ok(service.findAll());}

    @GetMapping("/{id}")
    public ResponseEntity<RegistroOperativo> findById(@PathVariable UUID id) {return ResponseEntity.ok(service.findById(id));}

    @PostMapping("/save")
    public ResponseEntity<RegistroOperativo> save(@RequestBody RegistroOperativo registroOperativo) {return ResponseEntity.ok(service.save(registroOperativo));}

    @PutMapping("/update")
    public ResponseEntity<RegistroOperativo> update(@RequestBody RegistroOperativo registroOperativo) {
        RegistroOperativo i = service.findById(registroOperativo.getId());
        i.setConsumo(registroOperativo.getConsumo());
        i.setInicio(registroOperativo.getInicio());
        i.setFin(registroOperativo.getInicio());
        i.setEstado(registroOperativo.getEstado());
        i.setEquipo(registroOperativo.getEquipo());

        return ResponseEntity.ok(service.save(i));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/cambiar-estado/{equipoId}")
    public ResponseEntity<String> cambiarEstado(@PathVariable UUID equipoId, @RequestBody Estado estado){
        Equipo equipo = equipoService.findById(equipoId);

        service.cambiarEstado(equipo, estado);

        return ResponseEntity.ok("Estado Actualizado");
    }
}
