package com.github.carlossfelipe.api_transacao.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record TransacaoResponseDTO(String remetente,
                                   BigDecimal valor,
                                   UUID id,
                                   OffsetDateTime data,
                                   String destinatario) {
}
