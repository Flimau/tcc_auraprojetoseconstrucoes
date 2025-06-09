package com.tccfer.application.controller.dto.auth;

import lombok.Data;

@Data
public class UsuarioLoginDTO { //Somente autentica
    private String login;
    private String senha;
}
