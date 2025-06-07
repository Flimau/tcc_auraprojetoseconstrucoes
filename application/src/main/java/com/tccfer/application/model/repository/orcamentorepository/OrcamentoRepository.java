package com.tccfer.application.model.repository.orcamentorepository;

import com.tccfer.application.model.entity.orcamento.Orcamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrcamentoRepository extends JpaRepository<Orcamento,Long> {

    /**
     * Retorna todos orçamentos cujo cliente.nome contenha (ignore case) o texto passado.
     */
    List<Orcamento> findByClienteNomeContainingIgnoreCase(String clienteNome);
}
