package com.smartenergy.backendapi.service;

import com.smartenergy.backendapi.model.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public abstract class BaseService<T extends BaseEntity, R extends JpaRepository<T, UUID>> {

    protected final R repo;

    protected BaseService(R repo) {
        this.repo = repo;
    }

    public List<T> findAll() {
        return repo.findAll();
    }

    public T findById(UUID id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("No encontrado"));
    }

    public T save(T entity) {
        return repo.save(entity);
    }

    public void delete(UUID id) {
        repo.deleteById(id);
    }
}
