package com.tccfer.application.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Perfil{
    //TODO: org.springframework.security.core.GrantedAuthority ver alternaativa para

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;

    private LocalDateTime dataRegistro = LocalDateTime.now();

    public Perfil(String descricao) {
        this.descricao = descricao;
    }

//    @Override
//    public String getAuthority() {
//        return descricao;
//    }
}

