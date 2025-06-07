package com.tccfer.application.controller.dto.usuario;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioMinDTO {//para usar em listagens - saida parcial
    Long id;
    String nome;
    String documento;
    String tipoUsuario;
    boolean ativo;
}
