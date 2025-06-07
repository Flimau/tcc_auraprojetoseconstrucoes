package com.tccfer.application.controller.dto.usuario;

import com.tccfer.application.model.entity.enuns.TipoUsuario;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UsuarioCadastroDTO {

    @NotBlank(message = "Campo login é obrigatório.")
    private String login;

    @NotBlank(message = "Campo senha não pode ser vazio.")
    private String senha;

    @NotNull(message = "Selecione o tipo de usuário.")
    private TipoUsuario tipoUsuario;

    @Valid
    @NotNull(message = "Dados da pessoa são obrigatórios")
    private PessoaCadastroDTO pessoaCadastroDTO;
}
//Cadastrar ususario
