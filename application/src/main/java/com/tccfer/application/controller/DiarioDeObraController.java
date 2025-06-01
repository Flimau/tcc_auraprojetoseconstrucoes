package com.tccfer.application.controller;

import com.tccfer.application.controller.dto.obra.DiarioDeObraDTO;
import com.tccfer.application.model.service.DiarioDeObraService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/obras/{obraId}/diarios")
public class DiarioDeObraController {

    private final DiarioDeObraService diarioService;

    public DiarioDeObraController(DiarioDeObraService diarioService) {
        this.diarioService = diarioService;
    }

    /**
     * GET /api/obras/{obraId}/diarios
     * Retorna todos os registros de Diário para a obra especificada.
     */
    @GetMapping
    public ResponseEntity<List<DiarioDeObraDTO>> listarPorObra(@PathVariable Long obraId) {
        List<DiarioDeObraDTO> lista = diarioService.listarPorObraId(obraId);
        return ResponseEntity.ok(lista);
    }

    /**
     * POST /api/obras/{obraId}/diarios
     * Cria um novo registro de Diário para a obra.
     */
    @PostMapping
    public ResponseEntity<DiarioDeObraDTO> criarDiario(
            @PathVariable Long obraId,
            @RequestBody DiarioDeObraDTO dto) {
        DiarioDeObraDTO criado = diarioService.criarDiario(obraId, dto);
        return new ResponseEntity<>(criado, HttpStatus.CREATED);
    }

    /**
     * PUT /api/obras/{obraId}/diarios/{id}
     * Atualiza um registro de Diário existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DiarioDeObraDTO> atualizarDiario(
            @PathVariable Long obraId,
            @PathVariable Long id,
            @RequestBody DiarioDeObraDTO dto) {
        DiarioDeObraDTO atualizado = diarioService.atualizarDiario(obraId, id, dto);
        return ResponseEntity.ok(atualizado);
    }

    /**
     * DELETE /api/obras/{obraId}/diarios/{id}
     * Exclui o registro de Diário especificado.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarDiario(
            @PathVariable Long obraId,
            @PathVariable Long id) {
        diarioService.deletarDiario(obraId, id);
        return ResponseEntity.noContent().build();
    }
}
