package com.tccfer.application.model.entity;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estados_id")
    private Estado estados;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "municipios_id")
    private Municipios municipios;

    private String logradouro;

    private String numero;
}
