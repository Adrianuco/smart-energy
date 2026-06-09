package com.smartenergy.backendapi.repository;

import com.smartenergy.backendapi.model.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IAdministradorRepository extends JpaRepository<Administrador, UUID> {
}
