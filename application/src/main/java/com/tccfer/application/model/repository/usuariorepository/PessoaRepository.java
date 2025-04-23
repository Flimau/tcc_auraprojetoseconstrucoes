package com.tccfer.application.model.repository.usuariorepository;

import com.tccfer.application.model.entity.pessoa.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
}
