package com.tccfer.application.controller.dto;

import lombok.Data;

@Data
public class EnderecoDTO {

    private String logradouro;
    private String numero;
    private String cidade;
    private String estado;
    private String cep;
}
