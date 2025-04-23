package com.tccfer.application.model.entity.localizacao;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estados_id")
    private Estado estado;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "municipios_id")
    private Municipio municipio;

    private String logradouro;

    private String numero;

    private String bairro;

    private String cep;
    }
