package com.tccfer.application.model.repository.obrarepository;

import com.tccfer.application.model.entity.obra.Obra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ObraRepository extends JpaRepository<Obra,Long> {

    /**
     * Busca uma Obra pelo ID e já carrega o cliente (Pessoa) em um JOIN FETCH,
     * para que o Hibernate inicialize o proxy de Pessoa dentro da mesma sessão.
     */
    @Query("SELECT o FROM Obra o JOIN FETCH o.cliente WHERE o.id = :id")
    Optional<Obra> findByIdWithCliente(@Param("id") Long id);

    /**
     * Lista todas as Obras e carrega também o cliente (Pessoa) em JOIN FETCH.
     */
    @Query("SELECT o FROM Obra o JOIN FETCH o.cliente")
    List<Obra> findAllWithCliente();

}
