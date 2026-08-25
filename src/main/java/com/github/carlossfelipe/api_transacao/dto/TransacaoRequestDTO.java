package com.github.carlossfelipe.api_transacao.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record TransacaoRequestDTO(String remetente,
                                  BigDecimal valor,
                                  String chaveTransacao) {
}
