package com.smartenergy.backendapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Edificio")
public class Edificio extends BaseEntity{

    //TODO [Reverse Engineering] generate columns from DB
}