package com.tccfer.application.model.service;

import com.tccfer.application.controller.dto.visita.VisitaTecnicaCadastroDTO;
import com.tccfer.application.controller.dto.visita.VisitaTecnicaDTO;
import com.tccfer.application.model.entity.visitas.VisitaTecnica;
import com.tccfer.application.model.repository.localizacaorepository.EnderecoRepository;
import com.tccfer.application.model.repository.usuariorepository.PessoaRepository;
import com.tccfer.application.mapper.EnderecoMapper;
import com.tccfer.application.model.repository.visitarepository.VisitaTecnicaRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class VisitaTecnicaService {

    private final VisitaTecnicaRepository visitaRepo;
    private final PessoaRepository pessoaRepo;
    private final EnderecoMapper enderecoMapper;
    private final EnderecoRepository enderecoRepo;

    @Value("${upload.visitas.dir}")
    private String uploadDir;

    public VisitaTecnicaService(
            VisitaTecnicaRepository visitaRepo,
            PessoaRepository pessoaRepo,
            EnderecoMapper enderecoMapper,
            EnderecoRepository enderecoRepo
    ) {
        this.visitaRepo = visitaRepo;
        this.pessoaRepo = pessoaRepo;
        this.enderecoMapper = enderecoMapper;
        this.enderecoRepo = enderecoRepo;
    }

    /**
     * Cria uma nova Visita Técnica a partir do DTO de cadastro.
     */
    public VisitaTecnicaDTO criarVisita(VisitaTecnicaCadastroDTO dto) {
        // busca cliente
        var cliente = pessoaRepo.findById(dto.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        // mapeia e salva endereço
        var enderecoEntity = enderecoMapper.toEntity(dto.getEndereco());
        enderecoEntity = enderecoRepo.save(enderecoEntity);

        // monta entidade
        var visita = new VisitaTecnica();
        visita.setCliente(cliente);
        visita.setEndereco(enderecoEntity);
        visita.setDescricao(dto.getDescricao());
        visita.setDataVisita(LocalDate.parse(dto.getDataVisita()));
        visita.setFotos(dto.getFotos());
        visita.setVideos(dto.getVideos());
        visita.setUsadaEmOrcamento(false);

        // persiste visita
        visita = visitaRepo.save(visita);

        // converte para DTO de retorno
        return mapToDTO(visita);
    }

    /**
     * Lista todas as visitas técnicas cadastradas.
     */
    public List<VisitaTecnicaDTO> listarVisitas() {
        return visitaRepo.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Mapeia VisitaTecnica → VisitaTecnicaDTO
     */
    private VisitaTecnicaDTO mapToDTO(VisitaTecnica visita) {
        VisitaTecnicaDTO dto = new VisitaTecnicaDTO();
        dto.setId(visita.getId());
        dto.setClienteId(visita.getCliente().getId());
        dto.setClienteNome(visita.getCliente().getNome());
        dto.setEndereco(enderecoMapper.toDTO(visita.getEndereco()));
        dto.setDescricao(visita.getDescricao());
        dto.setDataVisita(visita.getDataVisita().toString());
        dto.setFotos(visita.getFotos());
        dto.setVideos(visita.getVideos());
        dto.setUsadaEmOrcamento(visita.isUsadaEmOrcamento());
        return dto;
    }

    public VisitaTecnicaDTO atualizarVisita(Long id, VisitaTecnicaCadastroDTO dto) {
        // 1) Busca a visita ou lança exceção se não existir
        VisitaTecnica visita = visitaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Visita não encontrada"));

        // 2) Atualiza o endereço (mapear e salvar)
        var enderecoEntity = enderecoMapper.toEntity(dto.getEndereco());
        enderecoEntity = enderecoRepo.save(enderecoEntity);
        visita.setEndereco(enderecoEntity);

        // 3) Atualiza demais campos
        visita.setDescricao(dto.getDescricao());
        visita.setDataVisita(LocalDate.parse(dto.getDataVisita()));
        visita.setFotos(dto.getFotos());
        visita.setVideos(dto.getVideos());

        // 4) Persiste e retorna o DTO
        visita = visitaRepo.save(visita);
        return mapToDTO(visita);
    }

    /**
     * Exclui permanentemente uma Visita Técnica pelo ID.
     */
    public void deletarVisita(Long id) {
        if (!visitaRepo.existsById(id)) {
            throw new RuntimeException("Visita não encontrada para deleção");
        }
        VisitaTecnica visita = visitaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Visita não encontrada"));
        if (visita.getFotos() != null) {
            for (String url : visita.getFotos()) {
                try {
                    String nomeArquivo = Paths.get(url).getFileName().toString();
                    Path caminho = Paths.get(uploadDir).resolve(nomeArquivo);
                    Files.deleteIfExists(caminho);
                } catch (Exception ex) {
                    System.err.println("Erro ao excluir imagem: " + ex.getMessage());
                }
            }
        }

        visitaRepo.deleteById(id);
    }
}
