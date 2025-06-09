package com.tccfer.application.controller;

import com.tccfer.application.controller.dto.orcamento.OrcamentoCadastroDTO;
import com.tccfer.application.controller.dto.orcamento.OrcamentoDTO;
import com.tccfer.application.model.service.OrcamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/orcamento")
public class OrcamentoController {

    private final OrcamentoService service;

    @Autowired
    public OrcamentoController(OrcamentoService service) {
        this.service = service;
    }

    /** Cria um novo orçamento */
    @PostMapping
    public ResponseEntity<OrcamentoDTO> criar(@RequestBody OrcamentoCadastroDTO dto) {
        OrcamentoDTO criado = service.criarOrcamento(dto);
        return ResponseEntity
                .created(URI.create("/api/orcamento/" + criado.getId()))
                .body(criado);
    }

    /** Lista todos os orçamentos */
    @GetMapping
    public ResponseEntity<List<OrcamentoDTO>> listar() {
        List<OrcamentoDTO> lista = service.listarOrcamentos();
        return ResponseEntity.ok(lista);
    }

    /** Busca um orçamento por ID */
    @GetMapping("/{id}")
    public ResponseEntity<OrcamentoDTO> buscarPorId(@PathVariable Long id) {
        OrcamentoDTO dto = service.buscarPorId(id);
        return ResponseEntity.ok(dto);
    }

    /** Atualiza um orçamento existente */
    @PutMapping("/{id}")
    public ResponseEntity<OrcamentoDTO> atualizar(
            @PathVariable Long id,
            @RequestBody OrcamentoCadastroDTO dto
    ) {
        OrcamentoDTO atualizado = service.atualizarOrcamento(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    /** Exclui permanentemente um orçamento */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.deletarOrcamento(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> gerarPdf(@PathVariable Long id) {
        byte[] pdf = service.gerarPdfOrcamento(id); // você vai criar isso no serviço
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=orcamento-" + id + ".pdf")
                .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                .body(pdf);
    }

}
