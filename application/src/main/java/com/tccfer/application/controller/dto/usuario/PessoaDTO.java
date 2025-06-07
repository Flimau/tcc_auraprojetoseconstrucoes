package com.tccfer.application.controller.dto.usuario;

import com.tccfer.application.controller.dto.contato.ContatoDTO;
import com.tccfer.application.controller.dto.endereco.EnderecoDTO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PessoaDTO {
    private String nome;
    private String cpf;
    private String cnpj;
    private String tipoPessoa;
    private String sexo;
    private String dataNascimento;
    private EnderecoDTO endereco;
    private ContatoDTO contato;
}
