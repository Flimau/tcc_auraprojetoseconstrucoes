package com.tccfer.application.model.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("MESTRE_OBRA")
public class MestreObra extends Pessoa{

}
