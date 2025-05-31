package com.tccfer.application.model.repository.localizacaorepository;

import com.tccfer.application.model.entity.localizacao.Estado;
import org.springframework.data.domain.Sort;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstadoRepository extends JpaRepository<Estado,Long> {

    Optional<Estado> findBySigla(String sigla);
}
