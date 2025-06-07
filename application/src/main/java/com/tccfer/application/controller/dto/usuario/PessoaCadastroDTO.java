package com.tccfer.application.controller.dto.usuario;

import com.tccfer.application.controller.dto.contato.ContatoDTO;
import com.tccfer.application.controller.dto.endereco.EnderecoDTO;
import com.tccfer.application.model.entity.enuns.Sexo;
import com.tccfer.application.model.entity.enuns.TipoPessoa;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class PessoaCadastroDTO {

    @NotBlank(message = "Nome não deve ser em branco.")
    private String nome;
    private String cpf;
    private String cnpj;
    private Date dataNascimento;
    private TipoPessoa tipoPessoa;
    private Sexo sexo;

    @Valid
    @NotNull(message="Endereço é obrigatório.")
    private EnderecoDTO endereco;

    @Valid
    private ContatoDTO contato;
}
//PARA POST - DADOS PESSOAIS + ENDERECO