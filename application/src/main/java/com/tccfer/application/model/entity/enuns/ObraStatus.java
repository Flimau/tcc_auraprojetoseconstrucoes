package com.tccfer.application.model.entity.enuns;

public enum ObraStatus {
    PLANEJADA,      // Obra foi registrada, mas ainda não começou
    EM_ANDAMENTO,   // Obra está em execução
    PENDENTE,       // Alguma pendência (financeira, material, documento) está impedindo o progresso
    CONCLUIDA,      // Obra já foi finalizada
    CANCELADA
}
