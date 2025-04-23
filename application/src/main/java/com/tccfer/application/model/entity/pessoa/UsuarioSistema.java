package com.tccfer.application.model.entity.pessoa;

import com.tccfer.application.model.entity.enuns.TipoUsuario;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class UsuarioSistema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String senha;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pessoa_id", nullable = false)
    private Pessoa pessoa;

    private String tokenAtivacao;

    private boolean ativo = false;

}
