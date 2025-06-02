package com.tccfer.application.model.repository.obrarepository;

import com.tccfer.application.model.entity.obra.Obra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ObraRepository extends JpaRepository<Obra,Long> {

    /**
     * Busca uma Obra pelo ID, carregando Cliente, Orçamento e Executor juntos,
     * para evitar LazyInitializationException ao acessar qualquer um deles.
     */
    @Query("SELECT o " +
            "FROM Obra o " +
            "JOIN FETCH o.cliente " +
            "JOIN FETCH o.orcamento " +
            "LEFT JOIN FETCH o.executor " +
            "WHERE o.id = :id")
    Optional<Obra> findByIdWithClienteOrcamentoExecutor(@Param("id") Long id);

    /**
     * Lista todas as Obras, carregando Cliente, Orçamento e Executor em JOIN FETCH.
     */
    @Query("SELECT DISTINCT o " +
            "FROM Obra o " +
            "JOIN FETCH o.cliente " +
            "JOIN FETCH o.orcamento " +
            "LEFT JOIN FETCH o.executor")
    List<Obra> findAllWithClienteOrcamentoExecutor();

    /**
     * Conta quantas obras, atribuídas a este executor, ainda não estão concluídas
     * nem canceladas, e cujo cronograma se sobrepõe ao intervalo [novoInicio, novoFim].
     */
    @Query("SELECT COUNT(o) " +
            "FROM Obra o " +
            "WHERE o.executor.id = :executorId " +
            "  AND o.status NOT IN ('CONCLUIDA','CANCELADA') " +
            "  AND o.dataInicio <= :novoFim " +
            "  AND o.dataFim >= :novoInicio")
    Long countConflitos(
            @Param("executorId") Long executorId,
            @Param("novoInicio") LocalDate novoInicio,
            @Param("novoFim") LocalDate novoFim);

    /**
     * Retorna todas as obras com o status informado, carregando o cliente em JOIN FETCH.
     */
    @Query("SELECT o FROM Obra o JOIN FETCH o.cliente WHERE o.status = :status")
    List<Obra> findByStatusWithCliente(@Param("status") String status);

    /**
     * Retorna todas as obras cujo cronograma (dataInicio–dataFim)
     * se sobrepõe ao intervalo [dataInicio, dataFim] fornecido.
     * Carrega o cliente via JOIN FETCH.
     */
    @Query("SELECT o FROM Obra o JOIN FETCH o.cliente " +
            "WHERE o.dataInicio <= :dataFim AND o.dataFim >= :dataInicio")
    List<Obra> findByPeriodoWithCliente(
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim);

}
