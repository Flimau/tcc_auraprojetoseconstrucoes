package com.tccfer.application.model.repository;

import com.tccfer.application.model.entity.Estado;
import org.springframework.data.domain.Sort;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface EstadoRepository extends JpaRepository<Estado,Long> {

    @Query(value = "SELECT p FROM Estado p")
    List<Estado> findAll(Sort sort);

    Optional<Estado> findBySigla(String sigla);
}
