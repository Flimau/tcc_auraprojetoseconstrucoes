package com.tccfer.application.model.repository.localizacaorepository;

import com.tccfer.application.model.entity.localizacao.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}
