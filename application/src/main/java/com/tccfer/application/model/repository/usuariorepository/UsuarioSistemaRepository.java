package com.tccfer.application.model.repository.usuariorepository;

import com.tccfer.application.controller.dto.usuario.UsuarioSistemaDTO;
import com.tccfer.application.model.entity.enuns.TipoUsuario;
import com.tccfer.application.model.entity.pessoa.UsuarioSistema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioSistemaRepository extends JpaRepository<UsuarioSistema, Long> {
    boolean existsByLogin(String login);

    // Busca por CPF exato
    List<UsuarioSistemaDTO> findByPessoaCpf(String cpf);

    // Busca por CNPJ exato
    List<UsuarioSistemaDTO> findByPessoaCnpj(String cnpj);

    // Busca por nome contendo (case-insensitive)
    List<UsuarioSistemaDTO> findByPessoaNomeContainingIgnoreCase(String nome);

    // 1) Tipo + ID exato
    List<UsuarioSistemaDTO> findByTipoUsuarioAndId(TipoUsuario tipoUsuario, Long id);

    // 2) Tipo + CPF
    List<UsuarioSistemaDTO> findByTipoUsuarioAndPessoaCpf(TipoUsuario tipoUsuario, String cpf);

    // 3) Tipo + CNPJ
    List<UsuarioSistemaDTO> findByTipoUsuarioAndPessoaCnpj(TipoUsuario tipoUsuario, String cnpj);

    // 4) Tipo + nome (contendo, case-insensitive)
    List<UsuarioSistemaDTO> findByTipoUsuarioAndPessoaNomeContainingIgnoreCase(TipoUsuario tipoUsuario, String nome);

    List<UsuarioSistema> findByTipoUsuario(TipoUsuario tipo);

    Optional<UsuarioSistema> findByTokenAtivacao(String token);

    Optional<UsuarioSistema> findByLogin(String login);
}
