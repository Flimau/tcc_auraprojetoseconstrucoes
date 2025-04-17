package com.tccfer.application.model.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;


@Entity
@DiscriminatorValue("CLIENTE")
public class Cliente extends Pessoa{

}
