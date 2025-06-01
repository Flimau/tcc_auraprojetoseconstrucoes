package com.tccfer.application.model.service;

import com.tccfer.application.controller.dto.obra.DiarioDeObraDTO;
import com.tccfer.application.model.entity.obra.DiarioDeObra;
import com.tccfer.application.model.entity.obra.Obra;
import com.tccfer.application.model.repository.obrarepository.DiarioDeObraRepository;
import com.tccfer.application.model.repository.obrarepository.ObraRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiarioDeObraService {

    private final DiarioDeObraRepository diarioRepository;
    private final ObraRepository obraRepository;

    public DiarioDeObraService(
            DiarioDeObraRepository diarioRepository,
            ObraRepository obraRepository) {
        this.diarioRepository = diarioRepository;
        this.obraRepository = obraRepository;
    }

    /**
     * Lista todos os registros de diário de uma obra.
     */
    public List<DiarioDeObraDTO> listarPorObraId(Long obraId) {
        // Primeiro, garante que a obra exista
        Obra obra = obraRepository.findById(obraId)
                .orElseThrow(() -> new RuntimeException("Obra não encontrada com id: " + obraId));

        // Busca todos os diários dessa obra
        List<DiarioDeObra> diarios = diarioRepository.findByObraIdWithItens(obraId);

        // Converte cada entidade em DTO
        return diarios.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Cria um novo registro de Diário de Obra para a obra especificada.
     */
    public DiarioDeObraDTO criarDiario(Long obraId, DiarioDeObraDTO dto) {
        Obra obra = obraRepository.findById(obraId)
                .orElseThrow(() -> new RuntimeException("Obra não encontrada com id: " + obraId));

        DiarioDeObra diario = new DiarioDeObra();
        diario.setObra(obra);
        diario.setDataRegistro(dto.getDataRegistro());
        diario.setItens(dto.getItens());
        diario.setObservacoes(dto.getObservacoes());

        DiarioDeObra salvo = diarioRepository.save(diario);
        return toDTO(salvo);
    }

    /**
     * Atualiza um registro de Diário de Obra existente.
     */
    public DiarioDeObraDTO atualizarDiario(Long obraId, Long diarioId, DiarioDeObraDTO dto) {
        // Verifica se obra existe
        Obra obra = obraRepository.findById(obraId)
                .orElseThrow(() -> new RuntimeException("Obra não encontrada com id: " + obraId));

        DiarioDeObra diarioExistente = diarioRepository.findById(diarioId)
                .orElseThrow(() -> new RuntimeException("Diário não encontrado com id: " + diarioId));

        // Opcional: checar se diárioExistente.getObra().getId().equals(obraId),
        // para garantir que o diário pertence àquela obra.

        diarioExistente.setDataRegistro(dto.getDataRegistro());
        diarioExistente.setItens(dto.getItens());
        diarioExistente.setObservacoes(dto.getObservacoes());

        DiarioDeObra salvo = diarioRepository.save(diarioExistente);
        return toDTO(salvo);
    }

    /**
     * Deleta um registro de Diário de Obra pelo ID.
     */
    public void deletarDiario(Long obraId, Long diarioId) {
        Obra obra = obraRepository.findById(obraId)
                .orElseThrow(() -> new RuntimeException("Obra não encontrada com id: " + obraId));

        DiarioDeObra diarioExistente = diarioRepository.findById(diarioId)
                .orElseThrow(() -> new RuntimeException("Diário não encontrado com id: " + diarioId));

        // Opcional: checar se diarioExistente.getObra().getId().equals(obraId)

        diarioRepository.delete(diarioExistente);
    }

    /**
     * Converte entidade DiarioObra em DTO.
     */
    private DiarioDeObraDTO toDTO(DiarioDeObra diario) {
        return new DiarioDeObraDTO(
                diario.getId(),
                diario.getObra().getId(),
                diario.getDataRegistro(),
                diario.getItens(),
                diario.getObservacoes()
        );
    }
}
