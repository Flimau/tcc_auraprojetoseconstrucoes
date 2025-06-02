package com.tccfer.application.controller;

import com.tccfer.application.controller.dto.obra.ObraDTO;
import com.tccfer.application.model.entity.obra.Obra;
import com.tccfer.application.model.service.ObraService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/obras")
public class ObraController {

    private final ObraService obraService;

    public ObraController(ObraService obraService) {
        this.obraService = obraService;
    }

    /**
     * GET /api/obras
     * Retorna a lista de todas as obras, mas no formato DTO.
     */
    @GetMapping
    public ResponseEntity<List<ObraDTO>> listarTodas() {
        List<ObraDTO> listaDto = obraService.listarTodasDTO();
        return ResponseEntity.ok(listaDto);
    }

    /**
     * GET /api/obras/{id}
     * Retorna somente o ObraDTO correspondente.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ObraDTO> buscarPorId(@PathVariable Long id) {
        ObraDTO dto = obraService.buscarPorIdDTO(id);
        return ResponseEntity.ok(dto);
    }

    /**
     * POST /api/obras
     * Aceita no Body um JSON parecido com:
     * {
     *   "cliente": { "id": 1 },
     *   "orcamento": { "id": 2 },
     *   "dataInicio": "2025-06-15",
     *   "dataFim": "2025-07-10",
     *   "contratoUrl": "http://.../contrato.pdf"
     * }
     *
     * Internamente, JPA criará a Obra (gravando apenas as FK cliente_id e orcamento_id).
     * Depois retornamos o ObraDTO, só com os IDs no JSON (sem tentar trazer proxy).
     */
    @PostMapping
    public ResponseEntity<ObraDTO> criarObra(@RequestBody Obra obra) {
        ObraDTO criadaDto = obraService.criarObraDTO(obra);
        return new ResponseEntity<>(criadaDto, HttpStatus.CREATED);
    }

    /**
     * PUT /api/obras/{id}
     * Aceita no Body um JSON com os dados para atualizar:
     * {
     *   "cliente": { "id": 1 },
     *   "orcamento": { "id": 2 },
     *   "dataInicio": "2025-06-20",
     *   "dataFim": "2025-07-12",
     *   "contratoUrl": "http://.../novoContrato.pdf"
     * }
     *
     * Retorna o ObraDTO atualizado (somente IDs no JSON).
     */
    @PutMapping("/{id}")
    public ResponseEntity<ObraDTO> atualizarObra(
            @PathVariable Long id,
            @RequestBody Obra obraAtualizada) {
        ObraDTO atualDto = obraService.atualizarObraDTO(id, obraAtualizada);
        return ResponseEntity.ok(atualDto);
    }

    /**
     * DELETE /api/obras/{id}
     * Remove a Obra e retorna 204 No Content.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarObra(@PathVariable Long id) {
        obraService.deletarObra(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * PUT /api/obras/{id}/status/{novoStatus}
     * Altera o status da obra para um dos valores válidos do enum ObraStatus.
     */
    @PutMapping("/{id}/status/{novoStatus}")
    public ResponseEntity<ObraDTO> alterarStatus(
            @PathVariable("id") Long obraId,
            @PathVariable("novoStatus") String novoStatus) {

        ObraDTO dtoAtualizado = obraService.alterarStatus(obraId, novoStatus);
        return ResponseEntity.ok(dtoAtualizado);
    }

    /**
     * GET /api/obras/kanban
     * Retorna um mapa de listas de ObraDTO, agrupadas por status, para montar o Kanban.
     */
    @GetMapping("/kanban")
    public ResponseEntity<Map<String, List<ObraDTO>>> kanban() {
        Map<String, List<ObraDTO>> mapaKanban = obraService.obterPorKanban();
        return ResponseEntity.ok(mapaKanban);
    }

    /**
     * GET /api/obras/calendario?dataInicio=YYYY-MM-DD&dataFim=YYYY-MM-DD
     * Retorna lista de obras cujo cronograma se sobrepõe ao período informado.
     */
    @GetMapping("/calendario")
    public ResponseEntity<List<ObraDTO>> calendario(
            @RequestParam("dataInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam("dataFim")    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {

        List<ObraDTO> lista = obraService.obterPorPeriodo(dataInicio, dataFim);
        return ResponseEntity.ok(lista);
    }
}
