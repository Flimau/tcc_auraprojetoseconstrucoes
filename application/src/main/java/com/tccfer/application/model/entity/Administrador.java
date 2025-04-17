package com.tccfer.application.model.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ADMINISTRADOR")
public class Administrador extends Pessoa{

}
