package com.tccfer.application.model.repository.orcamentorepository;

import com.tccfer.application.model.entity.orcamento.Orcamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrcamentoRepository extends JpaRepository<Orcamento,Long> {
}
