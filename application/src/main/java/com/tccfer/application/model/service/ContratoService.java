package com.tccfer.application.model.service;

import com.tccfer.application.controller.dto.orcamento.OrcamentoDTO;
import com.tccfer.application.model.entity.orcamento.Orcamento;
import com.tccfer.application.model.repository.orcamentorepository.OrcamentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class ContratoService {

    private final OrcamentoRepository orcRepo;
    private final SpringTemplateEngine templateEngine;

    public ContratoService(OrcamentoRepository orcRepo,
                           SpringTemplateEngine templateEngine) {
        this.orcRepo = orcRepo;
        this.templateEngine = templateEngine;
    }

    /**
     * Gera um PDF de contrato para o orçamento identificado por `orcamentoId`.
     * Retorna o array de bytes do PDF.
     */
    public byte[] gerarContratoPdf(Long orcamentoId) {
        // 1) Busca a entidade Orcamento (com cliente, visita e itens)
        Orcamento orc = orcRepo.findById(orcamentoId)
                .orElseThrow(() -> new RuntimeException("Orçamento não encontrado: ID=" + orcamentoId));

        // 2) Converte Orcamento → OrcamentoDTO (útil para o Thymeleaf)
        OrcamentoDTO dto = mapOrcamentoParaDto(orc);

        // 3) Prepara o Context do Thymeleaf com todas as variáveis do template
        Context ctx = new Context();
        ctx.setVariable("clienteNome", dto.getClienteNome());

        // Descobre CPF ou CNPJ do cliente
        var cliente = orc.getCliente();
        String documento = (cliente.getTipoPessoa().name().equals("FISICA"))
                ? cliente.getCpf()
                : cliente.getCnpj();
        ctx.setVariable("clienteDocumento", documento);

        // Mapa de endereço do cliente
        var end = cliente.getEndereco();
        Map<String, Object> clienteEndereco = new HashMap<>();
        clienteEndereco.put("logradouro", end.getLogradouro());
        clienteEndereco.put("numero", end.getNumero());
        clienteEndereco.put("bairro", end.getBairro());
        clienteEndereco.put("cidade", end.getMunicipio().getNome());
        clienteEndereco.put("siglaEstado", end.getEstado().getSigla());
        clienteEndereco.put("cep", end.getCep());
        ctx.setVariable("clienteEndereco", clienteEndereco);

        ctx.setVariable("orcamentoId", dto.getId());

        String dataFormatada = orc.getDataCriacao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        ctx.setVariable("orcamentoData", dataFormatada);

        ctx.setVariable("orcamentoDescricao", dto.getDescricao());
        ctx.setVariable("tipo", dto.getTipo());
        ctx.setVariable("subtipo", dto.getSubtipo());
        ctx.setVariable("comMaterial", dto.isComMaterial());
        ctx.setVariable("itens", dto.getItens());

        // Calcula valorTotal (soma de item.valorUnitario * item.quantidade)
        BigDecimal valorTotal = dto.getItens().stream()
                .map(item -> item.getValorUnitario().multiply(new BigDecimal(item.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        ctx.setVariable("valorTotal", valorTotal);

        // 4) Renderiza o template Thymeleaf (contrato.html) para uma String HTML
        String htmlContent = templateEngine.process("contrato", ctx);

        // 5) Converte HTML → PDF usando OpenHTMLToPDF (PdfRendererBuilder)
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.withHtmlContent(htmlContent, null);
            builder.toStream(baos);
            builder.run();
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar PDF do contrato: " + e.getMessage(), e);
        }
    }

    /**
     * Mapeia a entidade Orcamento para o DTO usado no Thymeleaf.
     */
    private OrcamentoDTO mapOrcamentoParaDto(Orcamento orc) {
        OrcamentoDTO dto = new OrcamentoDTO();
        dto.setId(orc.getId());
        dto.setClienteId(orc.getCliente().getId());
        dto.setClienteNome(orc.getCliente().getNome());
        dto.setVisitaId(orc.getVisita() != null ? orc.getVisita().getId() : null);
        dto.setDescricao(orc.getDescricao());
        dto.setTipo(orc.getTipo().name());
        dto.setSubtipo(orc.getSubtipo().name());
        dto.setComMaterial(orc.isComMaterial());

        var itensDto = orc.getItens().stream().map(item -> {
            var i = new com.tccfer.application.controller.dto.orcamento.OrcamentoItemDTO();
            i.setId(item.getId());
            i.setDescricao(item.getDescricao());
            i.setQuantidade(item.getQuantidade());
            i.setValorUnitario(item.getValorUnitario());

            BigDecimal qtdItem = BigDecimal.valueOf(item.getQuantidade());
            BigDecimal subtotal = item.getValorUnitario().multiply(qtdItem);
            i.setSubtotal(subtotal);

            return i;
        }).toList();
        dto.setItens(itensDto);

        // Formata data de criação como ISO_LOCAL_DATE_TIME
        dto.setDataCriacao(orc.getDataCriacao().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        return dto;
    }
}
