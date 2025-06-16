package com.tccfer.application.model.repository.obrarepository;

import com.tccfer.application.model.entity.obra.DiarioDeObra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiarioDeObraRepository extends JpaRepository<DiarioDeObra, Long> {
    List<DiarioDeObra> findByObraId(Long obraId);
    }
