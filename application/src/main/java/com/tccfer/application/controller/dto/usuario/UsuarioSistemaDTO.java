package com.tccfer.application.controller.dto.usuario;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioSistemaDTO {//retorna para front
    private Long id;
    private String login;
    private String tipoUsuario;
    private PessoaDTO pessoa;
    private boolean ativo;
}
