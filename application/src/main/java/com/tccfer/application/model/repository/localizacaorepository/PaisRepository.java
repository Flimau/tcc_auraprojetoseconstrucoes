package com.tccfer.application.model.repository.localizacaorepository;

import com.tccfer.application.model.entity.localizacao.Pais;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaisRepository extends JpaRepository<Pais, Long> {
    Optional<Pais> findByNome(String nomePais);
}
