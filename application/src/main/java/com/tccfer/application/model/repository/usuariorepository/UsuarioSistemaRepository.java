package com.tccfer.application.model.repository.usuariorepository;

import com.tccfer.application.model.entity.pessoa.UsuarioSistema;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioSistemaRepository extends JpaRepository<UsuarioSistema, Long> {
}
