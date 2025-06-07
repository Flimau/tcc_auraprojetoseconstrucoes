package com.tccfer.application.controller.dto.usuario;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UsuarioAtivacaoDTO { //para ativacao - entrada de infos
    @NotBlank(message = "Token de ativação é obrigatório")
    private String token;

    @NotBlank(message = "Nova senha é obrigatória")
    private String novaSenha;
}
