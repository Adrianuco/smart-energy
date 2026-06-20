package com.smartenergy.backendapi.service;

import com.smartenergy.backendapi.dto.LoginResponse;
import com.smartenergy.backendapi.model.Administrador;
import com.smartenergy.backendapi.model.ApoyoLogistica;
import com.smartenergy.backendapi.repository.IAdministradorRepository;
import com.smartenergy.backendapi.repository.IApoyoLogisticaRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final IAdministradorRepository administradorRepository;
    private final IApoyoLogisticaRepository apoyoLogisticaRepository;

    public AuthService(
            IAdministradorRepository administradorRepository,
            IApoyoLogisticaRepository apoyoLogisticaRepository
    ) {
        this.administradorRepository = administradorRepository;
        this.apoyoLogisticaRepository = apoyoLogisticaRepository;
    }

    public LoginResponse login(String cif, String password) {

        Administrador admin =
                administradorRepository.findByCif(cif).orElse(null);

        if (admin != null) {

            if (!admin.getPassword().equals(password)) {
                throw new RuntimeException("Contraseña incorrecta");
            }

            LoginResponse response = new LoginResponse();

            response.setId(admin.getId());
            response.setNombre(admin.getNombre());
            response.setApellido(admin.getApellido());
            response.setRol("ADMINISTRADOR");

            return response;
        }

        ApoyoLogistica apoyo =
                apoyoLogisticaRepository.findByCif(cif).orElse(null);

        if (apoyo != null) {

            if (!apoyo.getPassword().equals(password)) {
                throw new RuntimeException("Contraseña incorrecta");
            }

            LoginResponse response = new LoginResponse();

            response.setId(apoyo.getId());
            response.setNombre(apoyo.getNombre());
            response.setApellido(apoyo.getApellido());
            response.setRol("APOYO_LOGISTICA");

            return response;
        }

        throw new RuntimeException("Usuario no encontrado");
    }
}
