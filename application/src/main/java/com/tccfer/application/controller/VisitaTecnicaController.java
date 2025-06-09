package com.tccfer.application.controller;


import com.tccfer.application.controller.dto.visita.VisitaTecnicaCadastroDTO;
import com.tccfer.application.controller.dto.visita.VisitaTecnicaDTO;
import com.tccfer.application.model.service.VisitaTecnicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/visitaTecnica")
public class VisitaTecnicaController {

    @Value("${upload.visitas.dir}")
    private String uploadDir;
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

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImagem(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("erro", "Arquivo vazio"));
            }

            // Gera nome único
            String nomeOriginal = StringUtils.cleanPath(file.getOriginalFilename());
            String extensao = nomeOriginal.substring(nomeOriginal.lastIndexOf("."));
            String nomeFinal = UUID.randomUUID() + extensao;

            // Garante que o diretório existe
            Path destino = Paths.get(uploadDir).toAbsolutePath().normalize();
            Files.createDirectories(destino);

            // Salva
            Path caminhoArquivo = destino.resolve(nomeFinal);
            Files.copy(file.getInputStream(), caminhoArquivo);

            // Retorna URL de acesso (ajuste se tiver domínio)
            String url = "/uploads/visitas/" + nomeFinal;

            return ResponseEntity.ok(Map.of("url", url));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("erro", "Erro ao salvar imagem: " + e.getMessage()));
        }
    }

    @DeleteMapping("/upload")
    public ResponseEntity<?> excluirImagem(@RequestParam("url") String urlRelativo) {
        try {
            // Exemplo de entrada: "/uploads/visitas/abc123.jpg"
            String nomeArquivo = Paths.get(urlRelativo).getFileName().toString();

            Path destino = Paths.get(uploadDir).resolve(nomeArquivo).normalize();

            if (!Files.exists(destino)) {
                return ResponseEntity.status(404).body(Map.of("erro", "Arquivo não encontrado"));
            }

            Files.delete(destino);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("erro", "Erro ao excluir imagem: " + e.getMessage()));
        }
    }
}
