package com.tccfer.application.model.repository.orcamentorepository;

import com.tccfer.application.model.entity.orcamento.OrcamentoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrcamentoItemRepository extends JpaRepository<OrcamentoItem,Long> {
}
