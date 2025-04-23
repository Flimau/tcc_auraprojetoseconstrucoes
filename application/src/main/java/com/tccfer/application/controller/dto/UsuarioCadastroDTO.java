package com.tccfer.application.controller.dto;

import com.tccfer.application.model.entity.enuns.TipoUsuario;
import lombok.Data;

@Data
public class UsuarioCadastroDTO {
    private String login;
    private String senha;
    private TipoUsuario tipo;
    private PessoaCadastroDTO pessoaCadastroDTO;
}
