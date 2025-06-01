package com.tccfer.application.model.service;

import com.tccfer.application.controller.dto.obra.ObraDTO;
import com.tccfer.application.model.entity.obra.Obra;
import com.tccfer.application.model.repository.obrarepository.ObraRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ObraService {

    private final ObraRepository obraRepository;

    public ObraService(ObraRepository obraRepository) {
        this.obraRepository = obraRepository;
    }

    /**
     * Lista todas as obras e converte cada Entidade para ObraDTO.
     */
    public List<ObraDTO> listarTodasDTO() {
        List<Obra> todas = obraRepository.findAllWithCliente();
        return todas.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca a obra pelo ID e retorna um ObraDTO.
     * Se não encontrar, lança RuntimeException.
     */
    public ObraDTO buscarPorIdDTO(Long id) {
        Obra obra = obraRepository.findByIdWithCliente(id)
                .orElseThrow(() -> new RuntimeException("Obra não encontrada com id: " + id));

        return toDTO(obra);
    }

    /**
     * Cria nova obra no banco e retorna o DTO correspondente.
     * Aqui podemos (1) salvar a entidade; (2) chamar findById para garantia de IDs,
     * mas, como no DTO só precisamos de FK, basta usar obraSalva.getCliente().getId() etc.
     */
    public ObraDTO criarObraDTO(Obra obra) {
        Obra obraSalva = obraRepository.save(obra);
        // Ao salvar, Hibernate grava apenas as FK cliente_id e orcamento_id.
        return toDTO(obraSalva);
    }

    /**
     * Atualiza uma obra já existente e retorna o DTO atualizado.
     */
    public ObraDTO atualizarObraDTO(Long id, Obra obraAtualizada) {
        Obra obraExistente = obraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Obra não encontrada com id: " + id));

        // Atualiza apenas os campos pertinentes:
        obraExistente.setCliente(obraAtualizada.getCliente());
        obraExistente.setOrcamento(obraAtualizada.getOrcamento());
        obraExistente.setDataInicio(obraAtualizada.getDataInicio());
        obraExistente.setDataFim(obraAtualizada.getDataFim());
        obraExistente.setContratoUrl(obraAtualizada.getContratoUrl());
        // (não mexe em diariosObra aqui)

        Obra obraSalva = obraRepository.save(obraExistente);
        return toDTO(obraSalva);
    }

    /**
     * Deleta uma obra pelo ID.
     */
    public void deletarObra(Long id) {
        Obra obraExistente = obraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Obra não encontrada com id: " + id));
        obraRepository.delete(obraExistente);
    }

    /**
     * Converte a entidade JPA Obra em ObraDTO “achatado”.
     */
    private ObraDTO toDTO(Obra obra) {
        // Obtem o ID do cliente (pode ser null, mas normalmente não é)
        Long clienteId = obra.getCliente() != null
                ? obra.getCliente().getId()
                : null;

        // Obtem o nome do cliente (se cliente não for null)
        String clienteNome = obra.getCliente() != null
                ? obra.getCliente().getNome()
                : null;

        // Obtem o ID do orçamento
        Long orcamentoId = obra.getOrcamento() != null
                ? obra.getOrcamento().getId()
                : null;

        return new ObraDTO(
                obra.getId(),
                clienteId,
                clienteNome,
                orcamentoId,
                obra.getDataInicio(),
                obra.getDataFim(),
                obra.getContratoUrl()
        );
    }
}
