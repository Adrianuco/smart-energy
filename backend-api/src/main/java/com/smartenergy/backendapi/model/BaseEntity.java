package com.smartenergy.backendapi.model;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity {
    @UuidGenerator
    @Id
    private UUID id;
}
