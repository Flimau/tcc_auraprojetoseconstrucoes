package com.tccfer.application.model.repository.contratorepository;

import com.tccfer.application.model.entity.contrato.Contrato;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContratoRepository extends JpaRepository<Contrato, Long> {

    /**
     * Busca o contrato associado a um determinado orçamento (1:1).
     */
    Optional<Contrato> findByOrcamentoId(Long orcamentoId);
}
