package com.tccfer.application.controller.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class PessoaDTO {

    private String nome;
    private String cpf;
    private Date dataNascimento;
    private String sexo;
    private EnderecoDTO endereco;
    private ContatoDTO contato;
    private LocalDateTime dataRegistro = LocalDateTime.now();

}
