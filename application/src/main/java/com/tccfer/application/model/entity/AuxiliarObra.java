package com.tccfer.application.model.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("AUXILIAR_OBRA")
public class AuxiliarObra extends Pessoa{

}
