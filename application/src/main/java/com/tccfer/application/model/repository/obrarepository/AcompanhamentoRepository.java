package com.tccfer.application.model.repository.obrarepository;

import com.tccfer.application.model.entity.obra.AcompanhamentoDiarioDeObra;
import com.tccfer.application.model.entity.obra.Obra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AcompanhamentoRepository extends JpaRepository<AcompanhamentoDiarioDeObra,Long> {

    List<AcompanhamentoDiarioDeObra> findByObraId(Long obraId);

    Optional<AcompanhamentoDiarioDeObra> findByObraIdAndDataRegistro(Long obraId, LocalDate dataRegistro);

    void deleteByObra(Obra obra);
}
