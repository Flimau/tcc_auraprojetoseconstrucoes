package com.tccfer.application.controller.dto;

import com.tccfer.application.model.entity.enuns.Sexo;
import com.tccfer.application.model.entity.enuns.TipoPessoa;
import com.tccfer.application.model.entity.enuns.TipoUsuario;
import lombok.Data;

import java.util.Date;

@Data
public class PessoaCadastroDTO {

    private String nome;
    private String cpf;
    private String cnpj;
    private Date dataNascimento;
    private TipoUsuario tipoUsuario;
    private TipoPessoa tipoPessoa;
    private Sexo sexo;
    private EnderecoDTO endereco;
    private ContatoDTO contato;
}
