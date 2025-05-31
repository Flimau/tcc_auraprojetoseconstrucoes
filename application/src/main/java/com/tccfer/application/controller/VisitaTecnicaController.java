package com.tccfer.application.controller;


import com.tccfer.application.controller.dto.visita.VisitaTecnicaCadastroDTO;
import com.tccfer.application.controller.dto.visita.VisitaTecnicaDTO;
import com.tccfer.application.model.service.VisitaTecnicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/visitaTecnica")
public class VisitaTecnicaController {

    private final VisitaTecnicaService service;

    @Autowired
    public VisitaTecnicaController(VisitaTecnicaService service) {
        this.service = service;
    }

    /** Cria nova visita técnica */
    @PostMapping
    public ResponseEntity<VisitaTecnicaDTO> criar(@RequestBody VisitaTecnicaCadastroDTO dto) {
        VisitaTecnicaDTO criado = service.criarVisita(dto);
        // Retorna 201 Created com Location:
        return ResponseEntity
                .created(URI.create("/api/visitaTecnica/" + criado.getId()))
                .body(criado);
    }

    /** Lista todas as visitas técnicas */
    @GetMapping
    public ResponseEntity<List<VisitaTecnicaDTO>> listar() {
        List<VisitaTecnicaDTO> lista = service.listarVisitas();
        return ResponseEntity.ok(lista);
    }

    /** Busca detalhe de uma visita por ID */
    @GetMapping("/{id}")
    public ResponseEntity<VisitaTecnicaDTO> buscarPorId(@PathVariable Long id) {
        VisitaTecnicaDTO dto = service.listarVisitas().stream()
                .filter(v -> v.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Visita não encontrada"));
        return ResponseEntity.ok(dto);
    }

    /** Atualiza uma visita existente */
    @PutMapping("/{id}")
    public ResponseEntity<VisitaTecnicaDTO> atualizar(
            @PathVariable Long id,
            @RequestBody VisitaTecnicaCadastroDTO dto
    ) {
        VisitaTecnicaDTO atualizado = service.atualizarVisita(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    /** Exclui permanentemente uma visita */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.deletarVisita(id);
        return ResponseEntity.noContent().build();
    }

}
