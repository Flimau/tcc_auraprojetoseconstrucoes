package com.tccfer.application.controller.dto.usuario;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioMinDTO {
    Long id;
    String nome;
    String documento;
    String tipoUsuario;
    boolean ativo;
}
