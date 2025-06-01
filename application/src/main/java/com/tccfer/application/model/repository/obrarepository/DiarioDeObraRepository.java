package com.tccfer.application.model.repository.obrarepository;

import com.tccfer.application.model.entity.obra.DiarioDeObra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiarioDeObraRepository extends JpaRepository<DiarioDeObra, Long> {
    List<DiarioDeObra> findByObraId(Long obraId);

    @Query("SELECT DISTINCT d " +
            "FROM DiarioDeObra d " +
            "JOIN FETCH d.itens i " +
            "WHERE d.obra.id = :obraId")
    List<DiarioDeObra> findByObraIdWithItens(@Param("obraId") Long obraId);

}
