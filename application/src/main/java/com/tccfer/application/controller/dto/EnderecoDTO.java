package com.tccfer.application.controller.dto;

import lombok.Data;

@Data
public class EnderecoDTO {

    private String logradouro;
    private String numero;
    private String bairro;
    private String cep;

    private String nomeMunicipio;
    private String siglaEstado;
    private String nomeEstado;
    private String nomePais;

}
