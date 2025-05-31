package com.tccfer.application.model.repository.usuariorepository;

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
    List<UsuarioSistema> findByPessoaCpf(String cpf);

    // Busca por CNPJ exato
    List<UsuarioSistema> findByPessoaCnpj(String cnpj);

    // Busca por nome contendo (case-insensitive)
    List<UsuarioSistema> findByPessoaNomeContainingIgnoreCase(String nome);

    // 1) Tipo + ID exato
    List<UsuarioSistema> findByTipoUsuarioAndId(TipoUsuario tipoUsuario, Long id);

    // 2) Tipo + CPF
    List<UsuarioSistema> findByTipoUsuarioAndPessoaCpf(TipoUsuario tipoUsuario, String cpf);

    // 3) Tipo + CNPJ
    List<UsuarioSistema> findByTipoUsuarioAndPessoaCnpj(TipoUsuario tipoUsuario, String cnpj);

    // 4) Tipo + nome (contendo, case-insensitive)
    List<UsuarioSistema> findByTipoUsuarioAndPessoaNomeContainingIgnoreCase(TipoUsuario tipoUsuario, String nome);

    List<UsuarioSistema> findByTipoUsuario(TipoUsuario tipo);

    Optional<UsuarioSistema> findByTokenAtivacao(String token);

}
