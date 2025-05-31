package com.tccfer.application.model.repository.localizacaorepository;

import com.tccfer.application.model.entity.localizacao.Estado;
import com.tccfer.application.model.entity.localizacao.Municipio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MunicipioRepository extends JpaRepository<Municipio, Long> {
    Optional<Municipio> findByNomeAndEstado(String nomeMunicipio, Estado estado);
}
