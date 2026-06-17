package com.smartenergy.backendapi.repository;

import com.smartenergy.backendapi.model.ConfigSistema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IConfigSistemaRepository extends JpaRepository<ConfigSistema, UUID> {

    Optional<ConfigSistema> findFirstByOrderByIdAsc();
}
