package com.tccfer.application.model.repository.visitarepository;

import com.tccfer.application.model.entity.visitas.VisitaTecnica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitaTecnicaRepository extends JpaRepository<VisitaTecnica,Long> {
}
